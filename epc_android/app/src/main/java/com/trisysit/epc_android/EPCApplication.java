package com.trisysit.epc_android;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.trisysit.epc_android.utils.SharedPrefHelper;

import io.fabric.sdk.android.Fabric;

/**
 * Created by tejeshkumardalai on 8/12/17.
 */

public class EPCApplication extends MultiDexApplication {
    private static Context applicationContext;

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        String userName= SharedPrefHelper.getInstance(this).getFromSharedPrefs(SharedPrefHelper.USERNAME);
        Crashlytics.setUserIdentifier(userName);
        if (applicationContext == null) {
            applicationContext = this;
        }
    }
}
