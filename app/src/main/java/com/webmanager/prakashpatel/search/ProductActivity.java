package com.webmanager.prakashpatel.search;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.webmanager.prakashpatel.search.databinding.ProductActivityBinding;
import com.webmanager.prakashpatel.search.model.Product;
import com.webmanager.prakashpatel.search.util.Animations;
import com.webmanager.prakashpatel.search.util.MyAdapter;


import java.io.IOException;

/**
 * Created by prakashpatel on 1/30/17.
 */

public class ProductActivity extends AppCompatActivity {

    private ProductActivityBinding binding;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Product product = (Product) getIntent().getSerializableExtra("product");
        setTitle(product.getBrandName());
        binding = DataBindingUtil.setContentView(this, R.layout.product_activity);

        //Add onclick event on addto cart button
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatingButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doAnimation(v);
            }
        });

        binding.setProduct(product);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.removeItem(R.id.search);
        return true;
    }

    public void doAnimation(View addToCartview){
        Log.d("inside doAnimation","doAnimation");
        int destLocation[] = new int[2];
        addToCartview.getLocationOnScreen(destLocation);

        int initialLocation[] = new int[2];
        TextView textView = (TextView) findViewById(R.id.brandName);
        textView.getLocationOnScreen(initialLocation);

        ObjectAnimator animX = ObjectAnimator.ofFloat(textView, "x",Math.abs(textView.getX() - addToCartview.getX()) );
        ObjectAnimator animY = ObjectAnimator.ofFloat(textView, "y", Math.abs(initialLocation[1] - destLocation[1]));
        ObjectAnimator scaleX= ObjectAnimator.ofFloat(textView,"scaleX",1.0f,0.f);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY,scaleX);
        animSetXY.setDuration(1000);
        animSetXY.start();


        animSetXY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                finish();
                startActivity(new Intent(ProductActivity.this,MainActivity.class));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void onBackPressed() {
        this.startActivity(new Intent(ProductActivity.this,MainActivity.class));
        return;
    }







}
