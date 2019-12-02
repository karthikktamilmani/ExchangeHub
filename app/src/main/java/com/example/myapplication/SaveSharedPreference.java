package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setLoggedIn(Context context, boolean loggedIn) {
        setLoggedIn(context, loggedIn, null);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn, String loggedInUser) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(CommonUtil.LOGGED_IN_PREF, loggedIn);
        editor.putString(CommonUtil.LOGGED_IN_USERID, loggedInUser);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(CommonUtil.LOGGED_IN_PREF, false);
    }

    public static String getLoggedInUser(Context context)
    {
        return getPreferences(context).getString(CommonUtil.LOGGED_IN_USERID, null);
    }

}
