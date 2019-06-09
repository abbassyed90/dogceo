package com.android.abbas.DogCEO.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.abbas.DogCEO.database.DatabaseClient;
import com.android.abbas.DogCEO.database.dao.BreedImageDao;
import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.database.entities.BreedImages;
import com.android.abbas.DogCEO.networking.ApiClient;
import com.android.abbas.DogCEO.networking.DogApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedImageRepository {

    private final BreedImageDao breedImageDao;

    private MutableLiveData<BreedImages> breedImagesMutableLiveData;

    public BreedImageRepository(Context context) {
        this.breedImageDao = DatabaseClient.getInstance(context).getAppDatabase()
                .breedImageDao();
        breedImagesMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<BreedImages> getBreeds(final Breed breed) {
        fetchBreedImages(breed);
        getFromLocalStorage(breed);
        return breedImagesMutableLiveData;
    }

    private void fetchBreedImages(final Breed breed) {
        final DogApi dogApi = ApiClient.getRetrofitInstance().create(DogApi.class);
        final Call<String> call = dogApi.getBreedImages(breed.getName());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject responseBodyJsonObject = new JSONObject(response.body());

                        if (responseBodyJsonObject.has("status")) {
                            final String status = responseBodyJsonObject.getString("status");
                            if (status.equalsIgnoreCase("success")) {
                                final BreedImages breedImages = parseBreedImages(responseBodyJsonObject, breed);

                                breedImagesMutableLiveData.setValue(breedImages);
                                saveBreedImages(breedImages);
                            }
                        }
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

    private BreedImages parseBreedImages(JSONObject responseBodyJsonObject, Breed breed) throws JSONException {
        final JSONArray messageJsonArray = responseBodyJsonObject.getJSONArray("message");
        final List<String> imageUrls = parseImageUrls(messageJsonArray);
        return new BreedImages(breed.getName(), imageUrls);
    }

    private List<String> parseImageUrls(JSONArray messageJsonArray) throws JSONException {
        final List<String> imageUrls = new ArrayList<>(messageJsonArray.length());
        for (int i = 0; i < messageJsonArray.length(); i++) {
            imageUrls.add(messageJsonArray.getString(i));
        }
        return imageUrls;
    }

    private void saveBreedImages(final BreedImages breedImages) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                breedImageDao.insert(breedImages);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void getFromLocalStorage(final Breed breed) {
        Single.fromCallable(new Callable<BreedImages>() {
            @Override
            public BreedImages call() throws Exception {
                return breedImageDao.getBreedImages(breed.getName());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BreedImages>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(BreedImages breedImages) {
                        breedImagesMutableLiveData.postValue(breedImages);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
}
