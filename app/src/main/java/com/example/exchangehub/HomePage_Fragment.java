package com.example.exchangehub;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.exchangehub.adapters.HomePagePostAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.ArrayList;

public class HomePage_Fragment extends Fragment implements View.OnClickListener {

    private IndicatorSeekBar distanceSeekBar;
    private TextInputEditText searchText;
    private ChipGroup filterChipGroup;
    private Chip beautyChip, accessoriesChip, computerChip, electronicschip, mobilesChip;
    private HomePagePostAdapter postAdapter;
    private ArrayList postLists;
    private RecyclerView recyclerView;
    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_page, container, false);

        //initializing views
        searchText = view.findViewById(R.id.searchViewId);
        recyclerView =view.findViewById(R.id.gridView);
        distanceSeekBar =view.findViewById(R.id.distanceProgress);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        postLists = new ArrayList();
        postLists.add("hhhhhhhhhhhhhhh");
        postLists.add("byee");
        postLists.add("haiii");
        postAdapter = new HomePagePostAdapter(postLists);
        recyclerView.setAdapter(postAdapter);


        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= searchText.getRight() - searchText.getTotalPaddingRight())
                    {
                        //calls the search method
                        //Toast.makeText(MainActivity.this, "eeeeeeeeeeeeeeeeee", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }

        });

        //
        distanceSeekBar.setOnSeekChangeListener(new OnSeekChangeListener(){

            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                searchPost();
            }
        });


        /**
         *
         * TODO:
         * change color for Chips
         *
         * select the color and uncomment this
         *
         filterChipGroup =view.findViewById(R.id.filter_chip);
         beautyChip =view.findViewById(R.id.beauty_chip);
         accessoriesChip =view.findViewById(R.id.accessories_chip);
         electronicschip =view.findViewById(R.id.electronics_chip);
         computerChip =view.findViewById(R.id.computer_chip);
         mobilesChip =view.findViewById(R.id.mobile_chip);



         CompoundButton.OnCheckedChangeListener filterChipListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
        buttonView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        else
        {
        buttonView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        //
        searchPost();
        }
        };
         beautyChip.setOnCheckedChangeListener(filterChipListener);
         accessoriesChip.setOnCheckedChangeListener(filterChipListener);
         computerChip.setOnCheckedChangeListener(filterChipListener);
         electronicschip.setOnCheckedChangeListener(filterChipListener);
         mobilesChip.setOnCheckedChangeListener(filterChipListener);

         */


        return view;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     *
     */
    public void searchPost()
    {
        /**
         *TODO: get all the parameters from the filters
         * and search for the post in the DB
         */
    }
}
