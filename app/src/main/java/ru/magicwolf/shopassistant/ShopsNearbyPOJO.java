package ru.magicwolf.shopassistant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopsNearbyPOJO {
    @SerializedName("shops")
    @Expose
    public List<Shop> shops;
}
