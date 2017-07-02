package ru.magicwolf.shopassistant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ShopGeo {
    @SerializedName("lat")
    @Expose
    public Double lat;

    @SerializedName("lng")
    @Expose
    public Double lng;
}
