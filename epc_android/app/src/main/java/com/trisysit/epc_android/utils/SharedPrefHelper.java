package com.trisysit.epc_android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tejeshkumardalai on 8/16/17.
 */

public class SharedPrefHelper {

    public static final String APP_NAME = "EPC";

    public static final String IMAGE1 = "image1";

    public static final String IMAGE2 = "image2";

    public static final String IMAGE3 = "image3";

    public static final String IMAGE4 = "image4";

    public static final String IMAGE5 = "image5";

    public static final String IMAGE6 = "image6";

    public static final String COMENT1 = "comment1";

    public static final String COMMENT2 = "comment2";

    public static final String COMMENT3 = "comment3";

    public static final String COMMENT4 = "comment4";

    public static final String COMMENT5 = "comment5";

    public static final String COMMENT6 = "comment6";

    public static final String GPS1="gps1";

    public static final String GPS2="gps2";

    public static final String GPS3="gps3";

    public static final String GPS4="gps4";

    public static final String GPS5="gps5";

    public static final String GPS6="gps6";

    public static final String GPS7="gps7";

    public static final String GPS8="gps8";

    public static final String IMAGE_TIME1="imageTime1";

    public static final String IMAGE_TIME2="imageTime2";

    public static final String IMAGE_TIME3="imageTime3";

    public static final String IMAGE_TIME4="imageTime4";

    public static final String IMAGE_TIME5="imageTime5";

    public static final String IMAGE_TIME6="imageTime6";

    public static final String IMAGE_TIME7="imageTime7";

    public static final String IMAGE_TIME8="imageTime8";

    public static final String ATTENDANCE_MOBILE_ID = "attendance_mobile_id";

    public static final String TOKEN = "token";

    public static final String  ROLE_NAME="role_name";

    public static final String NAME="name";

    public static final String IS_PUNCH_IN = "isPunchIn";

    public static final String PUNCH_IN_TIME = "punch_in_time";

    public static final String IS_PUNCH_OUT="is_punch_out";

    public static final String PUNCH_IN_DATE="punch_in_date";

    public static final String IS_FIRST_TIME_PUNCH_IN="is_first_time_punch_in";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String ENCODED_PASSWORD="encoded_password";

    public static final String  INGPS="inGps";

    public static final String OUTGPS="outGPS";

    public static final String COUNTER="counter";

    public static final String IS_FIRST_TIME_LOGIN="is_first_time_login";

    public static final String USER_ID="user_id";

    public static final String LAST_SYNC_TIME_TASK="lastSyncTimeTask";

    public static final String LAST_SYNC_TIME_ATTENDANCE="lastSyncTimeAttendance";

    public static final String PUNCHED_IN="punchedIn";

    public static final String IS_IMAGE_SYNC="isImageSync";


    public static final String IS_USER_MULTIPLE_ATT="isUserMultipleAtt";

    public static final String DEVICE_ID="device_id";





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
