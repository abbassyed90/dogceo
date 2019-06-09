package com.android.abbas.DogCEO.networking;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DogService {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://dog.ceo/api/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

    public void getAllBreads() {
        DogApi dogApi = getRetrofitInstance().create(DogApi.class);
        Call<String> call = dogApi.getAllBreads();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    /*
    public void getBreadImages(String breadName){
        DogApi dogApi = getRetrofitInstance().create(DogApi.class);
        Call<ResponseBody> call = dogApi.getBreedImages(breadName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("onResponse",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }
        });
    }

    public void getSubBreadImages(String breadName, String subBreadName){
        DogApi dogApi = getRetrofitInstance().create(DogApi.class);
        Call<ResponseBody> call = dogApi.getSubBreedImages(breadName,subBreadName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("onResponse",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }
        });
    }*/

}
