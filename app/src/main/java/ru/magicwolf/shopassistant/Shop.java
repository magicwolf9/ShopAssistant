package ru.magicwolf.shopassistant;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop {

    @SerializedName("shop_img")
    public String shopImg;

    @SerializedName("shop_name")
    @Expose
    public String shopName;

    @SerializedName("shop_description")
    public String shopDescription;
    @SerializedName("shop_geo")
    @Expose
    public ShopGeo shopGeo;

}

