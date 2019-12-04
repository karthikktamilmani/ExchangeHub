package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CommonUtil;
import com.example.myapplication.R;
import com.example.myapplication.SaveSharedPreference;
import com.example.myapplication.adapters.HomePagePostAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private IndicatorSeekBar distanceSeekBar;
    private TextInputEditText searchText;
    private ChipGroup filterChipGroup;
    private Chip beautyChip, accessoriesChip, computerChip, electronicschip, mobilesChip, furnitureChip;
    private HomePagePostAdapter postAdapter;
    private ArrayList postLists;
    private static ArrayList postListWithoutSearchCrit;
    private RecyclerView recyclerView;
    private static View view;
    //current location is hardcoded as Dalhousie university
    private final LatLng DEFAULT_LAT_LONG = new LatLng(44.633710, -63.593390);
    private static FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_page, container, false);

        //initializing views
        searchText = view.findViewById(R.id.searchViewId);
        recyclerView = view.findViewById(R.id.gridView);
        distanceSeekBar = view.findViewById(R.id.distanceProgress);
        filterChipGroup = view.findViewById(R.id.filter_chip);
        beautyChip = view.findViewById(R.id.beauty_chip);
        accessoriesChip = view.findViewById(R.id.accessories_chip);
        electronicschip = view.findViewById(R.id.electronics_chip);
        computerChip = view.findViewById(R.id.computer_chip);
        mobilesChip = view.findViewById(R.id.mobile_chip);
        furnitureChip = view.findViewById(R.id.furniture_chip);
        fragmentManager = getFragmentManager();
        //
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //
        if (postListWithoutSearchCrit == null || postListWithoutSearchCrit.size() == 0) {
            //
            if( postLists == null || postLists.size() == 0 ) {
                postLists = new ArrayList();
                //
                // TODO: //hardcoding the data -> since we are removing the aws instance
                HashMap prod1 = new HashMap();
                prod1.put("PRODUCT_TITLE", "iPhone XS Max 64gb Black");
                prod1.put("PRODUCT_PRICE", "CA$1000");
                prod1.put("PRODUCT_DESCRIPTION", "This is a brand new device. Warranty started only a month ago. It has never been out of the box.\n" +
                        "Wife wants to keep here smaller XS.");
                prod1.put("PRODUCT_IMAGE", R.drawable.xs_image);
                prod1.put("PRODUCT_LOCATION", new LatLng(44.633710, -63.593390));
                prod1.put("PRODUCT_CATEGORY", "Mobiles");
                postLists.add(prod1);
                prod1 = new HashMap();
                prod1.put("PRODUCT_TITLE", "64gb Apple Iphone XR");
                prod1.put("PRODUCT_PRICE", "CA$750");
                prod1.put("PRODUCT_DESCRIPTION", "Brand New Unlocked");
                prod1.put("PRODUCT_IMAGE", R.drawable.xr_image);
                prod1.put("PRODUCT_LOCATION", new LatLng(44.633710, -63.593390));
                prod1.put("PRODUCT_CATEGORY", "Mobiles");
                postLists.add(prod1);
                prod1 = new HashMap();
                prod1.put("PRODUCT_TITLE", "iPhone X 256gb");
                prod1.put("PRODUCT_PRICE", "CA$600");
                prod1.put("PRODUCT_DESCRIPTION", "Perfect condition and was always in a case. I am selling my sides Gold iPhone X with 256GB.\n" +
                        "Has been already unlocked.\n" +
                        "Comes with a real apple charger.");
                prod1.put("PRODUCT_IMAGE", R.drawable.x_image);
                prod1.put("PRODUCT_LOCATION", new LatLng(44.633710, -63.593390));
                prod1.put("PRODUCT_CATEGORY", "Mobiles");
                postLists.add(prod1);
                //
                prod1 = new HashMap();
                prod1.put("PRODUCT_TITLE", "Vintage gold furniture - Round chair, love seat & coffee table");
                prod1.put("PRODUCT_PRICE", "CA$250");
                prod1.put("PRODUCT_DESCRIPTION", "Cozy, three piece furniture set; with gold-on-gold wave pattern. Unique living room furniture in great shape but with minimal damage to love seat.");
                prod1.put("PRODUCT_IMAGE", R.drawable.round_chair);
                prod1.put("PRODUCT_LOCATION", new LatLng(44.533710, -63.593390));
                prod1.put("PRODUCT_CATEGORY", "Furniture");
                postLists.add(prod1);
                //
                prod1 = new HashMap();
                prod1.put("PRODUCT_TITLE", "Samsung Plasma TV 50");
                prod1.put("PRODUCT_PRICE", "CA$50");
                prod1.put("PRODUCT_DESCRIPTION", "Can't seem to get sound working and has lines during black screen on tv but not really noticeable during play. Does not have original remote.");
                prod1.put("PRODUCT_IMAGE", R.drawable.samsung);
                prod1.put("PRODUCT_LOCATION", new LatLng(44.633710, -63.493390));
                prod1.put("PRODUCT_CATEGORY", "Electronics");
                postLists.add(prod1);
                //
            }
            postListWithoutSearchCrit = postLists;
        }
        else
        {
            postLists = postListWithoutSearchCrit;
        }
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

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchPost();
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
        furnitureChip.setOnCheckedChangeListener(filterChipListener);


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
        Chip[] categoryArray = {beautyChip, accessoriesChip, computerChip, electronicschip, mobilesChip, furnitureChip};
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
                if( productMap.get("PRODUCT_LOCATION") instanceof LatLng) {
                    LatLng prdtLocation = (LatLng) productMap.get("PRODUCT_LOCATION");
                    //
                    float[] distanceValues = new float[3];
                    //
                    android.location.Location.distanceBetween(DEFAULT_LAT_LONG.latitude,
                            DEFAULT_LAT_LONG.longitude, prdtLocation.latitude,
                            prdtLocation.longitude, distanceValues);
                    //
                    if (distanceSeekBar.getProgress() < 20 || distanceValues[0] < distanceSeekBar.getProgress()) {
                        postLists.add(productMap);
                    }
                }
                else
                {
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
    public static FragmentManager getMainFragmentManager()
    {
        return fragmentManager;
    }
    //
    public static ArrayList getOriginalPostList()
    {
        return postListWithoutSearchCrit;
    }
}
