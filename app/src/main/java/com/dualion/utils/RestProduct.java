package com.dualion.utils;

import com.dualion.model.ProductList;
import com.dualion.webserver.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestProduct {

    private static final String BASE_URL = "http://bd.mumus.es";
    private ProductService service;
    Gson gson;

    public RestProduct()
    {
         /*gson = new GsonBuilder()
                .registerTypeAdapter(ProductList.class,new DateTypeAdapter())
                .create();*/

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                //.setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(ProductService.class);
    }

    public ProductService getService()
    {
        return service;
    }


}
