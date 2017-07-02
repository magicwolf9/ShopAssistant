package ru.magicwolf.shopassistant;

import android.util.Log;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RequestAPI {
    @GET // TODO вставить url перед тестом
    Call<ShopsNearbyPOJO> getShops(@Url String url);

}
/*@GET("/mobile?func=user_poititon") // TODO вставить url перед тестом
    Call sendUserPosition(@Field("userLatitude") double userLatitude, @Field("userLongitude") double userLongitude);
*/