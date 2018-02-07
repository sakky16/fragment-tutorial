package com.trisysit.epc_task_android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by trisys on 6/2/18.
 */

public class SharedPrefHelper {

    public static final String APP_NAME = "EPC_TASK";

    public static final String NAME="name";

    public static final String TOKEN = "token";

    public static final String LAST_SYNC_TIME_TASK="lastSyncTimeTask";



    private static volatile SharedPrefHelper instance;

    private final SharedPreferences sharedPreferences;

    private SharedPrefHelper(final Context context) {
        sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefHelper getInstance(Context context) { //@todo remove context from the param
        SharedPrefHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (SharedPrefHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SharedPrefHelper(context.getApplicationContext());
                }
            }
        }
        return localInstance;
    }

    public void saveInSharedPrefs(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }


    public String getFromSharedPrefs(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void deleteFromSharedPrefs() {
        sharedPreferences.edit().clear().commit();
    }

    public void removeFromSharedPrefs(String name) {
        sharedPreferences.edit().remove(name).commit();
    }
}
