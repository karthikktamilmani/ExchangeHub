package com.example.exchangehub;

import androidx.fragment.app.FragmentManager;

public class CommonUtil {
    //Email Validation pattern
    public static final String regEx = "[a-zA-Z0-9]+[a-zA-Z0-9.%\\-\\+]*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,4}";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String HOMEPAGE_FRAGMENT = "HomePage_Fragment";
    //
    private static FragmentManager fragmentManager;
    private static CommonUtil object = null;
    //
    private CommonUtil()
    {

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

}
