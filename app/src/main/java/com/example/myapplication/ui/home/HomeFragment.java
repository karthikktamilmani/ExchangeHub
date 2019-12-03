package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class HomeFragment extends Fragment {
    public View view;
    ViewFlipper vf;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        int images[] = {R.drawable.slide1,R.drawable.slide2};

        vf = view.findViewById(R.id.flipper);

        for (int image : images)
        {
            flipper(image);
        }

        return view;
    }

    public void flipper(int image)
    {
        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setBackgroundResource(image);

        vf.addView(imageView);
        vf.setFlipInterval(4000);
        vf.setAutoStart(true);

        //Animation

        vf.setInAnimation(getActivity().getApplicationContext(),android.R.anim.slide_in_left);
        vf.setOutAnimation(getActivity().getApplicationContext(),android.R.anim.slide_out_right);

    }

    }
