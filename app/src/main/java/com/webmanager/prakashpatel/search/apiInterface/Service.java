package com.webmanager.prakashpatel.search.apiInterface;

import com.webmanager.prakashpatel.search.model.ProductList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by prakashpatel on 1/29/17.
 */

public interface Service {
    @GET("/Search?key=b743e26728e16b81da139182bb2094357c31d331")
    public Call<ProductList> getProductByUserQuery(@Query("term") String query);

}
