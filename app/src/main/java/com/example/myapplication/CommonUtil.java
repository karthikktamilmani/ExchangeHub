package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class CommonUtil {
    //Email Validation pattern
    public static final String regEx = "[a-zA-Z0-9]+[a-zA-Z0-9.%\\-\\+]*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,4}";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String HOMEPAGE_FRAGMENT = "HomePage_Fragment";
    public static final String LOGGED_IN_PREF = "logged_in_status";
    public static final String LOGGED_IN_USERID = "logged_user";
    public static final String VIEW_PRODUCT_FRAGMENT = "ViewProduct_Fragment";
    private String LoginURL = "http://3e69b915.ngrok.io/Login";
    //
    private static FragmentManager fragmentManager;
    private static CommonUtil object = null;
    private Context appContext = null;
    private static ActionBar mainActionBar = null;
    //
    private CommonUtil()
    {

    }
    //
    public void setAppContext(Context appContext)
    {
        this.appContext = appContext;
    }
    //

    public static CommonUtil getInstance()
    {
        if( object == null ) {
            object = new CommonUtil();
            fragmentManager = MainActivityStart.getMainFragmentManager();
        }
        return object;
    }
    //
   /* void showHomePageFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new HomePage_Fragment(),
                        CommonUtil.HOMEPAGE_FRAGMENT).commit();
    }*/
    void showLoginFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment(),
                        CommonUtil.Login_Fragment).commit();
    }

    void showErrorTextLayout(TextInputLayout elementObj, String errorMsg)
    {
        elementObj.setHelperTextEnabled(Boolean.TRUE);
        elementObj.setHelperText("Please enter the email address");
//        elementObj.setStartIconVisible(Boolean.TRUE);
//        elementObj.setStartIconDrawable(R.drawable.error);
        elementObj.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        elementObj.setEndIconDrawable(R.drawable.error);
    }

    void hideErrorTextLayout(TextInputLayout elementObj, int endIconMode )
    {
        elementObj.setHelperTextEnabled(Boolean.FALSE);
        elementObj.setEndIconMode(endIconMode);
//        elementObj.setStartIconVisible(Boolean.FALSE);

    }

    Context getAppContext()
    {
        return appContext;
    }

   /* Object loginToAccount(final String emailId, final String password)
    {
        // check the db and if true

        if(true)
        {
            // if true return the userID
            Object res = null;
            StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, LoginURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                System.out.println("Response from server");
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("userid");
                                System.out.println(Response);
                                res = Response;
                                //Toast.makeText(getContext(),Response,Toast.LENGTH_SHORT).show();
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());

                }
            })
            {
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email",emailId);
                    params.put("pass",password);
                    return params;
                }
            };
            MySingleton.getInstance(getAppContext()).addToRequestQueue(jsonObjRequest);
            return res;
        }
        return null;
    }*/

    public void flipper(int image, ViewFlipper vf)
    {
        ImageView imageView = new ImageView(appContext);
        imageView.setBackgroundResource(image);

        vf.addView(imageView);
        vf.setFlipInterval(4000);
        vf.setAutoStart(true);

        //Animation

        vf.setInAnimation(appContext,android.R.anim.slide_in_left);
        vf.setOutAnimation(appContext,android.R.anim.slide_out_right);

    }

    /**
     * TODO: enter the productId in DB
     * and also for activityLog, enter this table value too
     *
     * @param productMap
     * @return
     */
    public void markProductFavourite(HashMap productMap)
    {
        //
        productMap.put("IS_FAVOURITE", Boolean.TRUE);
        //
    }

    /**
     * TODO: remove DB entry
     * @param productMap
     */
    public void unmarkProductFavourite(HashMap productMap)
    {
        //
        productMap.put("IS_FAVOURITE", Boolean.FALSE);
        //
    }

    /**
     * hoping that the valueMap would be a hashmap of the values
     *
     * @param valueMap
     */
    public void showProductFragment(Object valueMap) {

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.nav_host_fragment, new ViewProduct_Fragment(valueMap),
                        CommonUtil.VIEW_PRODUCT_FRAGMENT)
                .addToBackStack(null)
                .commit();

        // to roll-back to the previous transaction -> addToBackStack(null)
    }

    public void showPreviousFragment()
    {
        HomeFragment.getMainFragmentManager().popBackStackImmediate();
    }

    //
    public void setTextFieldValuesFromObject(TextView textView, HashMap objectMap, String valueShown)
    {
        textView.setText(objectMap.get(valueShown).toString());
    }

    //
    public void setMainActionBar(ActionBar actionBar)
    {
        this.mainActionBar = actionBar;
    }

    //
    public ActionBar getMainActionBar()
    {
        return mainActionBar;
    }

    //
    public String getAddressFromLatLng( LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(appContext, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            return addresses.get(0).getAddressLine(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "errorrrrr";
        }
    }

    //
    public void logoutApp()
    {
        SaveSharedPreference.setLoggedIn(appContext, false, (String)null);
    }


}
