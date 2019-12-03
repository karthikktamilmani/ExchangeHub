package com.example.myapplication.ui.feeds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.FeedPageAdapter;
import com.example.myapplication.adapters.HomePagePostAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedsFragment extends Fragment {

    public View view;
    private RecyclerView recyclerView;
    private ArrayList postLists;
    private FeedPageAdapter feedPageAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feeds, container, false);
        //
        recyclerView = view.findViewById(R.id.feedrecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //
        postLists = new ArrayList();
        HashMap prod1 = new HashMap();
        prod1.put("PRODUCT_TITLE","iPhone XS");
        prod1.put("PRODUCT_PRICE","CA$300");
        prod1.put("PRODUCT_DESCRIPTION","for sale, get it for selfnlsd skdjfnkjsdn ksjfnklsd slkdnflksd");
        prod1.put("PRODUCT_IMAGE",R.drawable.xs_image);
        prod1.put("PRODUCT_LOCATION" , new LatLng(44.633710, -63.593390));
        prod1.put("PRODUCT_CATEGORY","Mobiles");
        postLists.add(prod1);
        prod1 = new HashMap();
        prod1.put("PRODUCT_TITLE","iPhone XR");
        prod1.put("PRODUCT_PRICE","CA$400");
        prod1.put("PRODUCT_DESCRIPTION","for sale, get it for selfnlsd skdjfnkjsdn ksjfnklsd slkdnflksd");
        prod1.put("PRODUCT_IMAGE",R.drawable.xr_image);
        prod1.put("PRODUCT_LOCATION" , new LatLng(44.633710, -63.593390));
        prod1.put("PRODUCT_CATEGORY","Accessories");
        postLists.add(prod1);
        prod1 = new HashMap();
        prod1.put("PRODUCT_TITLE","iPhone X");
        prod1.put("PRODUCT_PRICE","CA$100");
        prod1.put("PRODUCT_DESCRIPTION","for sale, get it for selfnlsd skdjfnkjsdn ksjfnklsd slkdnflksd");
        prod1.put("PRODUCT_IMAGE",R.drawable.x_image);
        prod1.put("PRODUCT_LOCATION" , new LatLng(44.633710, -63.593390));
        prod1.put("PRODUCT_CATEGORY","Beauty");
        postLists.add(prod1);
        //
        feedPageAdapter = new FeedPageAdapter(postLists);
        recyclerView.setAdapter(feedPageAdapter);
        //
        return view;
    }
}