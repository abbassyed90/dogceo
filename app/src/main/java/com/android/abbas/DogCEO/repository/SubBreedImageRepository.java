package com.android.abbas.DogCEO.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.abbas.DogCEO.database.DatabaseClient;
import com.android.abbas.DogCEO.database.dao.SubBreedImageDao;
import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.database.entities.SubBreed;
import com.android.abbas.DogCEO.database.entities.SubBreedImages;
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

public class SubBreedImageRepository {

    private final SubBreedImageDao breedImageDao;

    private MutableLiveData<SubBreedImages> breedImagesMutableLiveData;

    public SubBreedImageRepository(Context context) {
        this.breedImageDao = DatabaseClient.getInstance(context).getAppDatabase()
                .subBreedImageDao();
        breedImagesMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<SubBreedImages> getBreeds(final Breed breed, SubBreed subBreed) {
        fetchBreedImages(breed, subBreed);
        getFromLocalStorage(subBreed);
        return breedImagesMutableLiveData;
    }

    private void fetchBreedImages(final Breed breed, final SubBreed subBreed) {
        final DogApi dogApi = ApiClient.getRetrofitInstance().create(DogApi.class);
        final Call<String> call = dogApi.getSubBreedImages(breed.getName(), subBreed.getName());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject responseBodyJsonObject = new JSONObject(response.body());

                        if (responseBodyJsonObject.has("status")) {
                            final String status = responseBodyJsonObject.getString("status");
                            if (status.equalsIgnoreCase("success")) {
                                final SubBreedImages breedImages = parseBreedImages(responseBodyJsonObject, breed);

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

    private SubBreedImages parseBreedImages(JSONObject responseBodyJsonObject, Breed breed) throws JSONException {
        final JSONArray messageJsonArray = responseBodyJsonObject.getJSONArray("message");
        final List<String> imageUrls = parseImageUrls(messageJsonArray);
        return new SubBreedImages(breed.getName(), imageUrls);
    }

    private List<String> parseImageUrls(JSONArray messageJsonArray) throws JSONException {
        final List<String> imageUrls = new ArrayList<>(messageJsonArray.length());
        for (int i = 0; i < messageJsonArray.length(); i++) {
            imageUrls.add(messageJsonArray.getString(i));
        }
        return imageUrls;
    }

    private void saveBreedImages(final SubBreedImages breedImages) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                breedImageDao.insert(breedImages);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void getFromLocalStorage(final SubBreed subBreed) {
        Single.fromCallable(new Callable<SubBreedImages>() {
            @Override
            public SubBreedImages call() throws Exception {
                return breedImageDao.getSubBreed(subBreed.getName());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SubBreedImages>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(SubBreedImages breedImages) {
                        breedImagesMutableLiveData.postValue(breedImages);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

}
