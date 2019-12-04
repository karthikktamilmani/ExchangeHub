package com.example.myapplication.ui.Add;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.CommonUtil;
import com.example.myapplication.CustomToast;
import com.example.myapplication.HomePage_Fragment;
import com.example.myapplication.LocationUtil.PermissionUtils;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MainActivityStart;
import com.example.myapplication.MySingleton;
import com.example.myapplication.R;
import com.example.myapplication.ViewProduct_Fragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddFragment extends Fragment
        {
    private int GALLERYOPTION = 1, CAMERAOPTION = 2;
    private EditText product,description,price;
    private Spinner Category;
    private Button btn;
    private RelativeLayout rlPick;
    double latitude;
    double longitude;
    Bitmap image;
    TextView tvAddress;
    TextView tvEmpty;
    private ImageView prodtImage;
    public View view;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    private boolean mTrackingLocation;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());



        product = view.findViewById(R.id.productTitle);
        description = view.findViewById(R.id.descriptioneditText);
        price = view.findViewById(R.id.cost);
        Category = view.findViewById(R.id.spinner);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        btn = view.findViewById(R.id.button);
        rlPick = view.findViewById(R.id.rlPickLocation);
        prodtImage = view.findViewById(R.id.ProductImage);
        //
        final AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(getContext());
        DialogBuilder.setTitle("Select Action");
        String[] DialogBuilderItems = {
                "Gallery",
                "Camera" };
        DialogBuilder.setItems(DialogBuilderItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Gallery();
                                break;
                            case 1:
                                Camera();
                                break;
                        }
                    }
                });
        //
        prodtImage.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                DialogBuilder.show();
            }
        });
        //
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.categoryList,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        Category.setAdapter(staticAdapter);
        //
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
        //
        rlPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
                getAddress();

            }
        });

        return view;
    }
    public void Gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERYOPTION);
    }

    private void Camera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, CAMERAOPTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERYOPTION) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);


                    ByteArrayOutputStream bytestreamFormatData = new ByteArrayOutputStream();
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, bytestreamFormatData);
                    byte[] byteArrayData = bytestreamFormatData.toByteArray();
                    image = BitmapFactory.decodeByteArray(byteArrayData, 0, byteArrayData.length);
                    ImageView ib = view. findViewById(R.id.ProductImage);
                    ib.setImageBitmap(image);
                    Spinner staticSpinner = view.findViewById(R.id.spinner);

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            insertData();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERAOPTION) {
            Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, bytestream);
            byte[] byteArrayData = bytestream.toByteArray();
            image = BitmapFactory.decodeByteArray(byteArrayData, 0, byteArrayData.length);
            ImageView ib = view. findViewById(R.id.ProductImage);
            ib.setImageBitmap(image);
            Spinner staticSpinner = view.findViewById(R.id.spinner);

            // Create an ArrayAdapter using the string array and a default spinner
            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                    .createFromResource(getActivity(), R.array.categoryList,
                            android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            staticSpinner.setAdapter(staticAdapter);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertData();
                }
            });

        }
    }
    void insertData(){
        String productText,descriptionText,priceText,CategoryText;
        productText = product.getText().toString();
        descriptionText = description.getText().toString();
        priceText = price.getText().toString();
        CategoryText = Category.getSelectedItem().toString();


        if(productText.isEmpty() || descriptionText.isEmpty() || priceText.isEmpty()
                || CategoryText.isEmpty() || tvAddress.getText().toString().trim().equals("")){
            Toast.makeText(getActivity(),"Check all fields",Toast.LENGTH_LONG).show();
            //insertData();
        }
        else {
            // hardcoding for testing since db is switched off in aws
            HashMap newDataMap = new HashMap();
            newDataMap.put("PRODUCT_TITLE", productText);
            newDataMap.put("PRODUCT_PRICE", "CA$" + priceText);
            newDataMap.put("PRODUCT_DESCRIPTION", descriptionText);
            newDataMap.put("PRODUCT_IMAGE", prodtImage.getDrawable());
            newDataMap.put("PRODUCT_LOCATION", tvAddress.getText().toString());
            newDataMap.put("PRODUCT_CATEGORY", CategoryText);
            //
            HomeFragment.getOriginalPostList().add(newDataMap);
            //
            Toast.makeText(getActivity(), "Post added", Toast.LENGTH_LONG).show();
            //
            //load ViewProduct_Fragment
            FragmentTransaction transaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out);
            transaction.replace(R.id.nav_host_fragment, new AddFragment()).commit();
            //
            //requestService(productText,descriptionText,priceText,CategoryText);
        }
    }

    private void requestService(final String productText, final String descriptionText, final String priceText,final String categoryText) {

        String ServiceURL = "http://030cc8c3.ngrok.io/AddPostModule";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {

                    //jsonObject = new JSONObject(response);
                    //NAME = jsonObject.getString("name");
                    //MAIL = jsonObject.getString("email");
                    //Toast.makeText(getApplicationContext(),MAIL,Toast.LENGTH_LONG).show();
                    //Log.d("STATE",name);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                //OBTAIN id from Database
                String userID = "1";
                //System.out.println(desc);
                params.put("userID",userID);
                params.put("Product",productText);
                params.put("Description",descriptionText);
                params.put("Price",priceText);
                params.put("Category",categoryText);
                params.put("image",imageToString(image));
                return params;
            }

        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }
    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

            @SuppressLint("MissingPermission")
            private void getLastLocation(){
                if (checkPermissions()) {
                    if (isLocationEnabled()) {
                        mFusedLocationClient.getLastLocation().addOnCompleteListener(
                                new OnCompleteListener<Location>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Location> task) {
                                        Location location = task.getResult();
                                        if (location == null) {
                                            requestNewLocationData();
                                        } else {
                                            latitude = location.getLatitude();
                                            longitude = location.getLongitude();
                                            //System.out.println("Latitude:"+latitude);


                                        }
                                    }
                                }
                        );
                    } else {
                        Toast.makeText(getActivity(), "Turn on location", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                } else {
                    requestPermissions();
                }
            }

            public void getAddress()
            {

                Address locationAddress=getAddress(latitude,longitude);

                if(locationAddress!=null)
                {
                    String address = locationAddress.getAddressLine(0);
                    String address1 = locationAddress.getAddressLine(1);
                    String city = locationAddress.getLocality();
                    String state = locationAddress.getAdminArea();
                    String country = locationAddress.getCountryName();
                    String postalCode = locationAddress.getPostalCode();

                    String currentLocation;

                    if(!TextUtils.isEmpty(address))
                    {
                        currentLocation=address;

                        if (!TextUtils.isEmpty(address1))
                            //currentLocation+="\n"+address1;

                        if (!TextUtils.isEmpty(city))
                        {
                            currentLocation+="\n"+city;

                            if (!TextUtils.isEmpty(postalCode))
                                currentLocation+=" - "+postalCode;
                        }
                        else
                        {
                            if (!TextUtils.isEmpty(postalCode))
                                currentLocation+="\n"+postalCode;
                        }

                        if (!TextUtils.isEmpty(state))
                            currentLocation+="\n"+state;

                        if (!TextUtils.isEmpty(country))
                            currentLocation+="\n"+country;

                        tvEmpty.setVisibility(View.GONE);
                        tvAddress.setText(currentLocation);
                        tvAddress.setVisibility(View.VISIBLE);

                    }

                }

            }

            public Address getAddress(double latitude,double longitude)
            {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude,longitude, 1);// Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    if(addresses != null && addresses.size()>0) {
                        Address returnedAddress = addresses.get(0);
                        return returnedAddress;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;

            }




            @SuppressLint("MissingPermission")
            private void requestNewLocationData(){

                LocationRequest mLocationRequest = new LocationRequest();
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                mLocationRequest.setInterval(0);
                mLocationRequest.setFastestInterval(0);
                mLocationRequest.setNumUpdates(1);

                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                mFusedLocationClient.requestLocationUpdates(
                        mLocationRequest, mLocationCallback,
                        Looper.myLooper()
                );

            }

            private LocationCallback mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location mLastLocation = locationResult.getLastLocation();
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    //latTextView.setText(mLastLocation.getLatitude()+"");
                    //lonTextView.setText(mLastLocation.getLongitude()+"");
                }
            };

            private boolean checkPermissions() {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                return false;
            }

            private void requestPermissions() {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_ID
                );
            }

            private boolean isLocationEnabled() {
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                );
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (requestCode == PERMISSION_ID) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getLastLocation();
                    }
                }
            }

            @Override
            public void onResume(){
                super.onResume();
                if (checkPermissions()) {
                    getLastLocation();
                }

            }

}