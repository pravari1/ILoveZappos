package com.webmanager.prakashpatel.search.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.webmanager.prakashpatel.search.R;
import com.webmanager.prakashpatel.search.model.Product;
import com.webmanager.prakashpatel.search.model.ProductList;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by prakashpatel on 1/29/17.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private ProductList productList;
    private int productCount;
    MyAdapter(){

    }
    public MyAdapter(Context context, ProductList productList){
        this.context = context;
        this.productList = productList;
        this.productCount = Integer.parseInt(productList.getCurrentResultCount());
    }
    @Override
    public int getCount() {
        return productCount;
    }

    @Override
    public Object getItem(int position) {
        return productList.getResults().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =  (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row, (ViewGroup) convertView,false);
        if(position %2 ==0){
            row.setBackgroundResource(R.color.white);
        }else{
            row.setBackgroundResource(R.color.grey);
        }
        Product product = (Product) getItem(position);

        TextView brandNameView = (TextView) row.findViewById(R.id.brandName);//Brand Name
//        TextView discountView = (TextView) row.findViewById(R.id.discount);// Discount
        TextView priceView = (TextView) row.findViewById(R.id.price);// Current Price
        TextView prodNameView = (TextView) row.findViewById(R.id.prodName);//Product Desciption
        ImageView prodImgView = (ImageView) row.findViewById(R.id.imageViewBitMap);// Image

        brandNameView.setText(product.getBrandName());
        prodNameView.setText(product.getProductName());
//        discountView.setText("");
        String discount = product.getPercentOff();
        if(discount!= null && discount != "" && Double.parseDouble(discount.substring(0,discount.length()-1)) > 0.0){
//            discountView.setText(product.getPercentOff());
        }

        String url = product.getThumbnailImageUrl();
        Picasso.with(context)
                .load(url)
                .resize(50, 50)
                .centerCrop()
                .into(prodImgView);
//        product.setImageViewBitMap(prodImgView.getDrawingCache());
//        prodImgView.setImageBitmap(new RetriveBitMapImg().execute(new AsyncTask<String,Void,Void>()));
//        new DownloadFilesTask().execute(url1, url2, url3);
        priceView.setText(product.getPrice());

        return row;
    }


}