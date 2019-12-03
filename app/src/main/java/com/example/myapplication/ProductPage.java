package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class ProductPage extends AppCompatActivity {

    ViewFlipper vf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        int images[] = {R.drawable.slide1,R.drawable.slide2};

        vf = findViewById(R.id.flipper);

        for (int image : images)
        {
            flipper(image);
        }


    }

    public void flipper(int image)
    {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        vf.addView(imageView);
        vf.setFlipInterval(2000);
        vf.setAutoStart(true);

        //Animation

        vf.setInAnimation(this,android.R.anim.slide_in_left);
        vf.setOutAnimation(this,android.R.anim.slide_out_right);

    }
}
