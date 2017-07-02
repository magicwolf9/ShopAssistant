package ru.magicwolf.shopassistant;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetShopsAsync extends AsyncTask<Double, Integer, List<Shop>> {

    @Override
    protected List<Shop> doInBackground(final Double... params) { //params[0] = latitude, params[1] = longitude
        Log.i("INFO", "Creating retrofit...");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://82.202.226.65:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestAPI api = retrofit.create(RequestAPI.class);
        Log.i("INFO", "Retrofit is created");

        String url = "/mobile?func=search_nearby_favorite&lat=" + params[0].toString() + "&lon="+params[1].toString();
        Call<ShopsNearbyPOJO> call = api.getShops(url);

        try {
            ShopsNearbyPOJO shopList = call.execute().body();
            if(shopList != null) {
                Log.i("INFO", "Ready to return shops " + shopList.toString());
                return shopList.shops;
            }

        } catch (IOException e) {
            Log.i("INFO", "Error during GetShopAsync " + e.toString());
        }
        return null;
    }
}
