package com.dualion.adapter;


import com.dualion.model.Product;
import com.dualion.model.ProductList;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * {
 *  "products":
 *      [
 *          {"pid":"12","name":"Hola10","img":"http:\/\/bd.mumus.es\/img\/gravatar.png","price":"1.20","description":"ssddsssss"},
 *          {"pid":"13","name":"Hola11","img":"http:\/\/bd.mumus.es\/img\/canalsprojects.png","price":"1.20","description":"ssddsssss"}
 *      ],
 *      "success":1
 *      "message":"OK"
 *      }
 *
 */

public class ProductTypeAdapter extends TypeAdapter<ProductList>{
    @Override
    public void write(JsonWriter jsonWriter, ProductList productList) throws IOException {

        jsonWriter.beginObject();
        jsonWriter.name("products").beginArray();
        for (final Product product : productList.getProducts()){
            jsonWriter.beginObject();
            jsonWriter.name("pid").value(product.getPid());
            jsonWriter.name("name").value(product.getName());
            jsonWriter.name("img").value(product.getLinkImg());
            jsonWriter.name("price").value(product.getPrice());
            jsonWriter.name("description").value(product.getDescription());
            jsonWriter.endObject();
        }
        jsonWriter.endArray();
        jsonWriter.name("message").value(productList.getMessage());
        jsonWriter.name("success").value(productList.getSuccess());
        jsonWriter.endObject();
    }

    @Override
    public ProductList read(JsonReader jsonReader) throws IOException {

        String name;
        ProductList productList = new ProductList();

        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            name = jsonReader.nextName();
            if (name.compareToIgnoreCase("message") == 0) {
                productList.setMessage(jsonReader.nextString());
            }else if(name.compareToIgnoreCase("success") == 0){
                productList.setSuccess(jsonReader.nextInt());
            }else if (name.compareToIgnoreCase("products") == 0){
                jsonReader.beginArray();
                final List<Product> products = new ArrayList<Product>();
                while(jsonReader.hasNext()){
                    jsonReader.beginObject();
                    final Product product = new Product();
                    while (jsonReader.hasNext()){
                        name = jsonReader.nextName();
                        if(name.compareToIgnoreCase("pid") == 0){
                            product.setPid(jsonReader.nextString());
                        } else if (name.compareToIgnoreCase("name") == 0){
                            product.setName(jsonReader.nextString());
                        }else if (name.compareToIgnoreCase("img") == 0){
                            product.setLinkImg(jsonReader.nextString());
                        }else if (name.compareToIgnoreCase("price") == 0){
                            product.setPrice(jsonReader.nextString());
                        }else if (name.compareToIgnoreCase("description") == 0){
                            product.setDescription(jsonReader.nextString());
                        }
                    }
                    products.add(product);
                    jsonReader.endObject();
                }
                productList.setProducts(products);
                jsonReader.endArray();
            }
        }
        jsonReader.endObject();
        return productList;
    }
}
