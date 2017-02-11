package com.webmanager.prakashpatel.search.model;


import android.databinding.BindingAdapter;
import android.databinding.tool.util.L;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.webmanager.prakashpatel.search.R;

import java.io.Serializable;

public class Product implements Serializable {

    private String brandName;
    private String thumbnailImageUrl;
    private String productId;
    private String originalPrice;
    private String styleId;
    private String colorId;
    private String price;
    private String percentOff;
    private String productUrl;
    private String productName;
    private String discount;
    private Bitmap imageViewBitMap;

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param styleId
     * @param price
     * @param originalPrice
     * @param productUrl
     * @param colorId
     * @param productName
     * @param brandName
     * @param thumbnailImageUrl
     * @param percentOff
     * @param productId
     */
    public Product(String brandName, String thumbnailImageUrl, String productId, String originalPrice, String styleId, String colorId, String price, String percentOff, String productUrl, String productName) {
        super();
        this.brandName = brandName;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.productId = productId;
        this.originalPrice = originalPrice;
        this.styleId = styleId;
        this.colorId = colorId;
        this.price = price;
        this.percentOff = percentOff;
        this.productUrl = productUrl;
        this.productName = productName;
        this.discount = "";
        this.imageViewBitMap = null;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }



    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(String percentOff) {
        this.percentOff = percentOff;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Bitmap getImageViewBitMap() {
        return imageViewBitMap;
    }

    public void setImageViewBitMap(Bitmap imageViewBitMap) {
        this.imageViewBitMap = imageViewBitMap;
    }

    @BindingAdapter({"bind:thumbnailImageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .into(view);
    }

    @BindingAdapter({"bind:originalPrice","bind:price"})
    public static void loadImage(TextView view, String originalPrice,String price) {
        Log.d("********Price--",price+"%%%%%"+originalPrice);
        if(originalPrice.equalsIgnoreCase(price)) {
            view.setText("");
            view.setVisibility(View.INVISIBLE);
        }else{
            view.setText(originalPrice);
            view.setVisibility(View.VISIBLE);
            view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }



}