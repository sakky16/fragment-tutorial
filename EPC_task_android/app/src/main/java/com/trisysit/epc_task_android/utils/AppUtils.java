package com.trisysit.epc_task_android.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by trisys on 6/2/18.
 */

public class AppUtils {
    public static final String IN_PROGRESS="in_proress";
    public static final String ACTIVE="ACTIVE";

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else if (str.trim().equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
    public static String currentDateTime(Context context, String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(calendar.getTime());
    }
    public static boolean getSyncDetails(Context context) {
        boolean syncCheck = false;
        int taskUpdateSyncCheck = DataBaseManager.getInstance().getTaskListForSync().size();
        int syncCheckValue = taskUpdateSyncCheck;
        if (syncCheckValue > 0) {
            syncCheck = true;
        }
        return syncCheck;
    }
}
