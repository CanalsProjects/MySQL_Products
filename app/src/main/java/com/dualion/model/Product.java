package com.dualion.model;

import java.lang.annotation.Annotation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import android.graphics.drawable.Drawable;

public class Product {

    @Expose
    private String pid;

    @Expose
    private String name;

    @Expose
    private String price;

    @Expose
    private String description;

    @Expose
    @SerializedName("img")
    private String linkImg;

    @Expose(serialize = false, deserialize = false)
    private Drawable img;

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