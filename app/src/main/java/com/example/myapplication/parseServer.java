package com.example.myapplication;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;

public class parseServer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Log.i("result","before initialization");
        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("0dc678607d2493871083b26f5bfa48b261690162")
                .clientKey("6b903cb64b1ebf7cc35cafcf9e89ed0e0013df9f")
                .server("http://ec2-3-15-16-242.us-east-2.compute.amazonaws.com/parse")
                .build()
        );


        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }


}
