package com.example.exchangehub;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

public class ViewProduct_Fragment extends Fragment implements View.OnClickListener {

    private static View view;
    private ViewFlipper vf;

    public ViewProduct_Fragment(Object valuesMap) {
        /**
         * TODO: get value from dB and set the values
         * or while searching every data is fetched and is only replaced
         *
         */


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_product_layout, container, false);
        //
        vf = view.findViewById(R.id.flipper);

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
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
