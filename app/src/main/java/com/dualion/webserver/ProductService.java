package com.dualion.webserver;

import com.dualion.model.ProductList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface ProductService {

    // "/get_all_products.php"
    @GET("/get_all_products.php")
    public void getAllProductList(Callback<ProductList> callback);

    // "/get_all_products.php?start=0&end=10"
    @GET("/get_all_products.php")
    public void getLimitProductList(@Query("start") Integer numStart, @Query("end") Integer end, Callback<ProductList> callback);

    // "/get_all_products.php?q=Hol&start=0&end=2&tsort=0&sort=0"
    @GET("/get_all_products.php")
    public void getSearchProductList(@Query("q") String search, @Query("start") Integer numStart, @Query("end") Integer end, @Query("sort") Integer sort, @Query("tsort") Integer typeSort, Callback<ProductList> callback);



}
