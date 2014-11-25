package com.dualion.model;

import com.google.gson.annotations.SerializedName;
import android.graphics.drawable.Drawable;

//import org.json.JSONException;
//import org.json.JSONObject;

public class Product {

    /*private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_IMG = "img";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";*/

    private String pid;

    private String name;

    private String price;

    private String description;

    @SerializedName("img")
    private String linkImg;

    private Drawable img;

//    public Product(JSONObject obj) {
//
//        try {
//            // Storing each json item in variable
//            this.pid = obj.getString(TAG_PID);
//            this.name = obj.getString(TAG_NAME);
//            this.price = obj.getString(TAG_PRICE);
//            this.description = obj.getString(TAG_DESCRIPTION);
//            this.linkImg = obj.getString(TAG_IMG);
//            this.img = null;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

}