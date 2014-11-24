package com.dualion.model;

import java.util.ArrayList;
import java.util.List;
import java.lang.annotation.Annotation;
import com.google.gson.annotations.Expose;

public class ProductList {

    @Expose
    private List<Product> products = new ArrayList<Product>();

    @Expose
    private Integer success;

    //@Expose
    //private String message;

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

    /*public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }*/

}
