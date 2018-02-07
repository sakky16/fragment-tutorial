package com.trisysit.epc_task_android;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by trisys on 6/2/18.
 */

public class EPCTaskUpdateAppication extends MultiDexApplication {
    private static Context applicationContext;
    public static Context getContext() {
        return applicationContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        //String userName= SharedPrefHelper.getInstance(this).getFromSharedPrefs(SharedPrefHelper.USERNAME);
        //Crashlytics.setUserIdentifier(userName);
        if (applicationContext == null) {
            applicationContext = this;
        }
    }
}
