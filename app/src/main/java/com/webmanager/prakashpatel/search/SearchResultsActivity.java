package com.webmanager.prakashpatel.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.webmanager.prakashpatel.search.apiInterface.Service;
import com.webmanager.prakashpatel.search.model.Product;
import com.webmanager.prakashpatel.search.model.ProductList;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by prakashpatel on 1/28/17.
 */

public class SearchResultsActivity extends AppCompatActivity implements Callback<ProductList> {
    private ProgressBar progressBar;
    private TextView noResultFoundView;
    private static final String BASE_URL = "https://api.zappos.com";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_activity);
        Log.d("inside searchresult","SearchResultsActivity");
        noResultFoundView = (TextView) findViewById(R.id.noResultFound);
        noResultFoundView.setVisibility(View.GONE);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //REtrofil call;
            getResults(query);
        }
    }


    private  void showProductPage(Product product){
        Intent productActivity = new Intent(SearchResultsActivity.this,ProductActivity.class);
        productActivity.putExtra("product", (Serializable) product);
        startActivity(productActivity);
    }
    public void getResults(String query) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                //.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addNetworkInterceptor(new Interceptor() {

                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                // .addHeader(Constant.Header, authToken)
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        // Fetch All the product_activity first time
        Call<ProductList> productListCall = service.getProductByUserQuery(query);
        //asynchronous call
        productListCall.enqueue(this);
    }
    @Override
    public void onResponse(Call<ProductList> call, Response<ProductList> response) {
        ProductList productList = response.body();
        if(response.isSuccessful()){
            if(productList.getCurrentResultCount() != null && productList.getCurrentResultCount() != "" && Integer.parseInt(productList.getCurrentResultCount()) > 0 ){
                showProductPage(productList.getResults().get(0));
                progressBar.setVisibility(View.GONE);
            }else{
                // No result Found
                noResultFoundView = (TextView) findViewById(R.id.noResultFound);
                noResultFoundView.setVisibility(View.VISIBLE);
            }

        }else{
            Log.d("","In erron else null");
            noResultFoundView = (TextView) findViewById(R.id.noResultFound);
            noResultFoundView.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Call<ProductList> call, Throwable t) {
        noResultFoundView = (TextView) findViewById(R.id.noResultFound);
        noResultFoundView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}