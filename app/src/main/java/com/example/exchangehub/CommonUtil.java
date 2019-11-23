package com.example.exchangehub;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;

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
    //
    private static FragmentManager fragmentManager;
    private static CommonUtil object = null;
    private Context appContext = null;
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
                        CommonUtil.HOMEPAGE_FRAGMENT).commit();
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
            return null;
        }
        return null;
    }

}
