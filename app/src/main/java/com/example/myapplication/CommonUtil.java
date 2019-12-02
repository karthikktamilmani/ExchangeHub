package com.example.myapplication;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;

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
            fragmentManager = MainActivity.getMainFragmentManager();
        }
        return object;
    }
    //
    void showHomePageFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new HomePage_Fragment(),
                        CommonUtil.HOMEPAGE_FRAGMENT).commitAllowingStateLoss();
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

    void hideErrorTextLayout(TextInputLayout elementObj,int endIconMode )
    {
        elementObj.setHelperTextEnabled(Boolean.FALSE);
        elementObj.setEndIconMode(endIconMode);
//        elementObj.setStartIconVisible(Boolean.FALSE);

    }

    Context getAppContext()
    {
        return appContext;
    }

    Object loginToAccount(String emailId, String password)
    {
        // TODO::
        // check the db and if true
        if(true)
        {
            // if true return the userID
            return "1234";
        }
        return null;
    }

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
     * @param productId
     * @return
     */
    public void markProductFavourite(String productId)
    {
        //
    }

    /**
     * TODO: remove DB entry
     * @param productId
     */
    public void unmarkProductFavourite(String productId)
    {
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
                .replace(R.id.frameContainer, new ViewProduct_Fragment(valueMap),
                        CommonUtil.VIEW_PRODUCT_FRAGMENT)
                .addToBackStack(null)
                .commit();
        //


        // to roll-back to the previous transaction -> addToBackStack(null)
    }

    public void showPreviousFragment()
    {
        fragmentManager.popBackStackImmediate();
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

}
