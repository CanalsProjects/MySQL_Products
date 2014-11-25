package com.dualion.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ProductList {

    @Expose
    @SerializedName("products")
    private List<Product> products = new ArrayList<Product>();

    @Expose
    @SerializedName("success")
    private Integer success;

    @Expose
    @SerializedName("message")
    private String message;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
