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
        //
        feedPageAdapter = new FeedPageAdapter(postLists);
        recyclerView.setAdapter(feedPageAdapter);
        //
        return view;
    }
}