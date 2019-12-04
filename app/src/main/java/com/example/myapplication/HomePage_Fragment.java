package com.example.myapplication;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.myapplication.adapters.HomePagePostAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class HomePage_Fragment extends Fragment implements View.OnClickListener {

    private IndicatorSeekBar distanceSeekBar;
    private TextInputEditText searchText;
    private ChipGroup filterChipGroup;
    private Chip beautyChip, accessoriesChip, computerChip, electronicschip, mobilesChip;
    private HomePagePostAdapter postAdapter;
    private ArrayList postLists, postListWithoutSearchCrit;
    private RecyclerView recyclerView;
    private static View view;
    //current location is hardcoded as Dalhousie university
    private final LatLng DEFAULT_LAT_LONG = new LatLng(44.633710, -63.593390);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_page, container, false);

        //initializing views
        searchText = view.findViewById(R.id.searchViewId);
        recyclerView =view.findViewById(R.id.gridView);
        distanceSeekBar =view.findViewById(R.id.distanceProgress);
        filterChipGroup =view.findViewById(R.id.filter_chip);
        beautyChip =view.findViewById(R.id.beauty_chip);
        accessoriesChip =view.findViewById(R.id.accessories_chip);
        electronicschip =view.findViewById(R.id.electronics_chip);
        computerChip =view.findViewById(R.id.computer_chip);
        mobilesChip =view.findViewById(R.id.mobile_chip);
        //
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
        postListWithoutSearchCrit = postLists;
        //
        postAdapter = new HomePagePostAdapter(postLists);
        recyclerView.setAdapter(postAdapter);


        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= searchText.getRight() - searchText.getTotalPaddingRight())
                    {
                        //calls the search method
                        searchPost();
                        //Toast.makeText(MainActivity.class, "eeeeeeeeeeeeeeeeee", Toast.LENGTH_SHORT).show();
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


        CompoundButton.OnCheckedChangeListener filterChipListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /**
                 *
                 * change color for Chips
                 *
                 * select the color and uncomment this
                 */
                if(isChecked)
                {
                    ((Chip)buttonView).setChipBackgroundColorResource(R.color.button_selectorcolor);
                    ((Chip)buttonView).setCloseIcon(((Chip)buttonView).getCheckedIcon());
                    ((Chip)buttonView).setCloseIconVisible(true);
                    ((Chip)buttonView).setCheckedIconVisible(false);
                }
                else
                {
                    ((Chip)buttonView).setChipBackgroundColorResource(R.color.white_greyish);
                    ((Chip)buttonView).setCloseIconVisible(false);

                }
                //
                searchPost();
            }
        };

        beautyChip.setOnCheckedChangeListener(filterChipListener);
        //beautyChip.setCloseIcon();
        accessoriesChip.setOnCheckedChangeListener(filterChipListener);
        computerChip.setOnCheckedChangeListener(filterChipListener);
        electronicschip.setOnCheckedChangeListener(filterChipListener);
        mobilesChip.setOnCheckedChangeListener(filterChipListener);



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
         *getting all the parameters from the filters
         * and search for the post in the DB
         */
        Chip[] categoryArray = {beautyChip, accessoriesChip, computerChip, electronicschip, mobilesChip};
        ArrayList<Chip> categoryList = new ArrayList<Chip>();
        Collections.addAll(categoryList, categoryArray);
        //
        postLists = new ArrayList();
        //
        String searchTextCrit = null;
        if( searchText.getText() != null && !"".equals(searchText.getText().toString().trim()) )
        {
            searchTextCrit = searchText.getText().toString().toLowerCase();
        }
        //
        //find all the categories to be searched
        ArrayList<String> categoriesSelected = new ArrayList<String>();
        //
        for (Chip each_chip: categoryList) {
            //
            if( each_chip.isChecked() )
            {
                categoriesSelected.add(each_chip.getText().toString());
            }
            //
        }
        //
        String locationSelected = "";
        //
        Iterator<HashMap> productListIter = postListWithoutSearchCrit.iterator();
        while( productListIter.hasNext() )
        {
            HashMap productMap = productListIter.next();
            boolean doesTitleCritMatch = true;
            /*
                prod1.put("PRODUCT_TITLE","iPhone XS");
                prod1.put("PRODUCT_PRICE","300");
                prod1.put("PRODUCT_DESCRIPTION","for sale, get it for selfnlsd skdjfnkjsdn ksjfnklsd slkdnflksd");
                prod1.put("PRODUCT_IMAGE",R.drawable.xs_image);
                prod1.put("PRODUCT_LOCATION" , "some Lt and lon");
                prod1.put("PRODUCT_CATEGORY","");
             */
            //
            if( searchTextCrit != null ) {
                doesTitleCritMatch = false;
                String productTitle = productMap.get("PRODUCT_TITLE").toString().toLowerCase();
                //
                if(productTitle.contains(searchTextCrit) || searchTextCrit.contains(productTitle)) {
                    doesTitleCritMatch = true;
                }
            }
            //
            boolean doesCategoryMatch = false;
            if( doesTitleCritMatch )
            {
                doesCategoryMatch = true;
                String productCategory = productMap.get("PRODUCT_CATEGORY").toString();
                //
                if( categoriesSelected.size() > 0 )
                {
                    doesCategoryMatch = false;
                    if(categoriesSelected.contains(productCategory))
                    {
                        doesCategoryMatch = true;
                    }
                }
            }
            //
            if( doesCategoryMatch )
            {
                LatLng prdtLocation = (LatLng) productMap.get("PRODUCT_LOCATION");
                //
                float[] distanceValues = new float[3];
                //
                android.location.Location.distanceBetween(DEFAULT_LAT_LONG.latitude,
                        DEFAULT_LAT_LONG.longitude,prdtLocation.latitude,
                        prdtLocation.longitude,distanceValues);
                //
                if( distanceSeekBar.getProgress() == 0 || distanceValues[0] < distanceSeekBar.getProgress()) {
                    postLists.add(productMap);
                }
            }

            //
        }
        //
        postAdapter = new HomePagePostAdapter(postLists);
        recyclerView.setAdapter(postAdapter);
        //
    }
}
