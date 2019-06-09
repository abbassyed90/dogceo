package com.android.abbas.DogCEO.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.abbas.DogCEO.database.DatabaseClient;
import com.android.abbas.DogCEO.database.dao.BreedDao;
import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.database.entities.SubBreed;
import com.android.abbas.DogCEO.networking.ApiClient;
import com.android.abbas.DogCEO.networking.DogApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedRepository {

    private final BreedDao breedDao;

    public BreedRepository(Context context) {
        this.breedDao = DatabaseClient.getInstance(context).getAppDatabase()
                .breedDao();
    }

    public LiveData<List<Breed>> getBreeds() {
        fetchBreeds();
        return breedDao.getAll();
    }

    private void fetchBreeds() {
        final DogApi dogApi = ApiClient.getRetrofitInstance().create(DogApi.class);
        final Call<String> call = dogApi.getAllBreads();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject responseBodyJsonObject = new JSONObject(response.body());
                        final JSONObject messageJsonObject = responseBodyJsonObject.getJSONObject("message");

                        final List<Breed> breeds = parseBreeds(messageJsonObject);
                        saveBreeds(breeds);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    private List<Breed> parseBreeds(final JSONObject messageJsonObject) throws JSONException {
        final List<Breed> breeds = new ArrayList<>();

        final Iterator<String> messageKeysIterator = messageJsonObject.keys();
        while (messageKeysIterator.hasNext()) {

            final String key = messageKeysIterator.next();
            final JSONArray subBreedArray = messageJsonObject.getJSONArray(key);
            final List<SubBreed> subBreeds = parseSubBreed(subBreedArray);

            breeds.add(new Breed(key, subBreeds));
        }

        return breeds;
    }

    private List<SubBreed> parseSubBreed(JSONArray subBreedArray) throws JSONException {
        final List<SubBreed> subBreeds = new ArrayList<>(subBreedArray.length());
        for (int i = 0; i < subBreedArray.length(); i++) {
            final SubBreed subBreed = new SubBreed(subBreedArray.getString(i));
            subBreeds.add(subBreed);
        }
        return subBreeds;
    }


    private void saveBreeds(final List<Breed> breeds) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                breedDao.insert(breeds.toArray(new Breed[breeds.size()]));
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

}
