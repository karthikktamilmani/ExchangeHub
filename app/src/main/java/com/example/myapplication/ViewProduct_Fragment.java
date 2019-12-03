package com.example.myapplication;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class ViewProduct_Fragment extends Fragment implements View.OnClickListener {

    private static View view;
    private ViewFlipper vf;
    private TextView productTitle, productPrice, productDescription, productLocation;
    private ImageView prdtImg, backBtn, shareBtn;
    private HashMap objectValueMap;

    public ViewProduct_Fragment(Object valuesMap) {
        /**
         * TODO: get value from dB and set the values
         * or while searching every data is fetched and is only replaced
         *
         */
        this.objectValueMap = (HashMap) valuesMap;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_product_layout, container, false);
        //vf = view.findViewById(R.id.flipper);
        //
        //CommonUtil.getInstance().getMainActionBar().setDisplayHomeAsUpEnabled(true);
        //CommonUtil.getInstance().getMainActionBar().setHasOptionsMenu(true);
        //setHasOptionsMenu(true);
        //
        productTitle = view.findViewById(R.id.prodtitle);
        productPrice = view.findViewById(R.id.prodPrice);
        productDescription = view.findViewById(R.id.prodDescription);
        productLocation = view.findViewById(R.id.prodPlace);
        prdtImg = view.findViewById(R.id.flipper);
        backBtn = view.findViewById(R.id.backArrow);
        shareBtn = view.findViewById(R.id.shareImg);
        //
        //
        CommonUtil.getInstance().setTextFieldValuesFromObject(productTitle, objectValueMap, "PRODUCT_TITLE");
        CommonUtil.getInstance().setTextFieldValuesFromObject(productPrice, objectValueMap, "PRODUCT_PRICE");
        CommonUtil.getInstance().setTextFieldValuesFromObject(productDescription, objectValueMap, "PRODUCT_DESCRIPTION");
        //
        productLocation.setText("Pick From: " + CommonUtil.getInstance().getAddressFromLatLng((LatLng) objectValueMap.get("PRODUCT_LOCATION")));
        productLocation.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                openMaps(CommonUtil.getInstance().getAddressFromLatLng((LatLng) objectValueMap.get("PRODUCT_LOCATION")));
            }

        });
        //
        shareBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                shareEvent((String) objectValueMap.get("PRODUCT_TITLE"),(String) objectValueMap.get("PRODUCT_PRICE"),
                        (String) objectValueMap.get("PRODUCT_DESCRIPTION"),
                        CommonUtil.getInstance().getAddressFromLatLng((LatLng) objectValueMap.get("PRODUCT_LOCATION")));
            }
        });
        //
        prdtImg.setImageResource((int) objectValueMap.get("PRODUCT_IMAGE"));
        //
        //int images = (int)objectValueMap.get("PRODUCT_IMAGE");
        //CommonUtil.getInstance().flipper((int)objectValueMap.get("PRODUCT_IMAGE"),vf);
        //holder.productImage.setImageResource((int)objectValueMap.get("PRODUCT_IMAGE"));
        /**
         *
         * TODO: get the image from DB and use flipper to showcase them
         *
         *
         int images[] = {R.drawable.slide1,R.drawable.slide2};



         for (int image : images)
         {
         CommonUtil.getInstance().flipper(image,vf);
         }

        //

        OnBackPressedCallback callback = new OnBackPressedCallback(
                true // default to enabled
        ) {
            @Override
            public void handleOnBackPressed() {
                //CommonUtil.getInstance().getMainActionBar().setDisplayHomeAsUpEnabled(false);
                CommonUtil.getInstance().showPreviousFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(
                this, // LifecycleOwner
                callback);
        */
        //
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                CommonUtil.getInstance().showPreviousFragment();
                //
            }

        });
        //
        return view;
    }

    @Override
    public void onClick(View v) {
        //
        switch (v.getId()) {


        }
        //
    }

    //
    private void shareEvent(String prdtTitle, String prdtPrice, String prdtDescription, String address) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        String message = prdtTitle + "\nPrice: "+prdtPrice + "\n" + "Description: " +prdtDescription + "\n" + "Location: " + address;
        intent.putExtra(Intent.EXTRA_SUBJECT, prdtTitle);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("text/plain");
        /*
        if (imagepath != null && !imagepath.trim().isEmpty()) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imagepath));
            intent.setType("image/jpeg");
        }
         */
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_using)));
    }

    //
    private void openMaps(String address)
    {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}

