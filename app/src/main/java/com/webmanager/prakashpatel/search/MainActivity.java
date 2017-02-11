package com.webmanager.prakashpatel.search;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.webmanager.prakashpatel.search.model.Product;
import com.webmanager.prakashpatel.search.util.MyAdapter;
import com.webmanager.prakashpatel.search.apiInterface.Service;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,Callback<ProductList> {
    private ListView listView;
    private static final String BASE_URL = "https://api.zappos.com";
    private ProductList productList;
    private static boolean showProductPage = false;
    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";
    private RetainedFragment mDataFragment;
    private TextView noResultFoundView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity","Oncreate");
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        mDataFragment = (RetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);
        noResultFoundView = (TextView) findViewById(R.id.noResultFound);
        noResultFoundView.setVisibility(View.GONE);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        if (mDataFragment == null || mDataFragment.getListView() == null || mDataFragment.getListView() != null) {
            mDataFragment = new RetainedFragment();
            fm.beginTransaction().add(mDataFragment, TAG_RETAINED_FRAGMENT).commit();
//             load data from a data source or perform any calculation
            listView = (ListView) findViewById(R.id.myList);
            mDataFragment.setListView(listView);
            getResults("");

        }else
            listView = mDataFragment.getListView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        Log.d("inside search ------","88888888888888888888888888");
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.search) {
            onSearchRequested();
            Toast.makeText(MainActivity.this, "CLicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product product = (Product) parent.getItemAtPosition(position);
        Intent productActivity = new Intent(MainActivity.this,ProductActivity.class);
        productActivity.putExtra("product", (Serializable) product);
        startActivity(productActivity);
    }


    private  void showProductPage(Product product){
        Intent productActivity = new Intent(MainActivity.this,ProductActivity.class);
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
                listView.setAdapter(new MyAdapter(this,productList));
                mDataFragment.setListView(listView);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Product product = (Product) parent.getItemAtPosition(position);
                        showProductPage(product);
                    }
                    @SuppressWarnings("unused")
                    public void onClick(View v){
                    };
                });
            }else{
                // No result Found
                noResultFoundView.setText("No result found");
                noResultFoundView.setVisibility(View.VISIBLE);
            }

        }else{
            Log.d("","In erron else null");
            noResultFoundView.setText("No result found");
            noResultFoundView.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Call<ProductList> call, Throwable t) {
        noResultFoundView.setText(t.getMessage());
        noResultFoundView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
