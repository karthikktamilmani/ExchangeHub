package com.example.exchangehub;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class ViewProduct_Fragment extends Fragment implements View.OnClickListener  {

    private static View view;
    private ViewFlipper vf;
    private TextView productTitle, productPrice, productDescription, productLocation;
    private ImageView prdtImg;
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
        CommonUtil.getInstance().getMainActionBar().setDisplayHomeAsUpEnabled(true);
        //CommonUtil.getInstance().getMainActionBar().setHasOptionsMenu(true);
        setHasOptionsMenu(true);
        //
        productTitle = view.findViewById(R.id.prodtitle);
        productPrice = view.findViewById(R.id.prodPrice);
        productDescription = view.findViewById(R.id.prodDescription);
        productLocation = view.findViewById(R.id.prodPlace);
        prdtImg = view.findViewById(R.id.flipper);
        //
        //
        CommonUtil.getInstance().setTextFieldValuesFromObject(productTitle,objectValueMap,"PRODUCT_TITLE");
        CommonUtil.getInstance().setTextFieldValuesFromObject(productPrice,objectValueMap,"PRODUCT_PRICE");
        CommonUtil.getInstance().setTextFieldValuesFromObject(productDescription,objectValueMap,"PRODUCT_DESCRIPTION");
        //
        productLocation.setText( "Pick From: " + CommonUtil.getInstance().getAddressFromLatLng((LatLng) objectValueMap.get("PRODUCT_LOCATION")));
        prdtImg.setImageResource((int)objectValueMap.get("PRODUCT_IMAGE"));
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
         */

        //

        OnBackPressedCallback callback = new OnBackPressedCallback(
                true // default to enabled
        ) {
            @Override
            public void handleOnBackPressed() {
                CommonUtil.getInstance().getMainActionBar().setDisplayHomeAsUpEnabled(false);
                CommonUtil.getInstance().showPreviousFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(
                this, // LifecycleOwner
                callback);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //onBackPressed();
        //Log.d(TAG, "Fragment.onOptionsItemSelected");

        switch (item.getItemId()) {
            case android.R.id.home:
                CommonUtil.getInstance().getMainActionBar().setDisplayHomeAsUpEnabled(false);
                CommonUtil.getInstance().showPreviousFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
