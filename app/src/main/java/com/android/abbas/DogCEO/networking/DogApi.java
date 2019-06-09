package com.android.abbas.DogCEO.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogApi {

    @GET("breeds/list/all")
    Call<String> getAllBreads();

    @GET("breed/{breadName}/images")
    Call<String> getBreedImages(@Path("breadName") String breadName);

    @GET("breed/{breadName}/{subBreadName}/images")
    Call<String> getSubBreedImages(@Path("breadName") String breadName, @Path("subBreadName") String subBreadName);

}
