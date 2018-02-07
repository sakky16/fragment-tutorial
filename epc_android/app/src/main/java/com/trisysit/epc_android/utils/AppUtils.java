package com.trisysit.epc_android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.activity.SyncInfoActivity;
import com.trisysit.epc_android.model.ResponseJson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tejeshkumardalai on 8/14/17.
 */

public class AppUtils {

    public static final String IN_PROGRESS="in_proress";
    public static final String ACTIVE="ACTIVE";
    public static final String SYNC_COMPLETED="sync_completed";
    public static final String RECORD_ATTEN_PAGE="record_atten_page";
    public static final String ATTENDANCE_DETAILS_PAGE="attendace_details_page";
    public static final String DAILY_UPDATE_TYPE="dailyUpdate";
    public static final String ACTIVITY="activity";
    public static final String SUB_ACTIVITY="subActivity";
    public static final String ACTIVITY_UPDATE_TYPE="activity_update_type";
    public static final String SUBACTIVITY_UPDATE_TYPE="sub_activity_update_type";
    public static final String PUNCH_OUT="punch_out";
    public static final String SAVE="save";
    public static final String PUNCH_IN="punch_in";
    public static final String IMAGE_SYNCED="image_synced";
    public static final String ROLE_FIELD_MANAGER="ROLE_FIELDMANAGER";
    public static final String ROLE_ATTENDANCE_ADMIN ="ROLE_ATTENDANCE_ADMIN";
    public static final String IMAGE_NOT_SYNCED="image_not_synced";
    public static final String RECORD_ATTEN_PAGE_AFTER_SYN="record_atten_page_after_syn";


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
    public static boolean getSyncDetails(Context context){
        boolean syncCheck=false;
        int attendanceSyncCheck=DatabaaseUtils.getAttendListForSync(context,AppUtils.IN_PROGRESS).size();
        int taskUpdateSyncCheck=DataBaseManager.getInstance().getTaskListForSync().size();
        int imgaeSyncCheck=DataBaseManager.getInstance().getImageListByStatus().size();
        int syncCheckValue=attendanceSyncCheck+taskUpdateSyncCheck+imgaeSyncCheck;
        if(syncCheckValue>0){
            syncCheck=true;
        }
        return syncCheck;
    }
    public static void onClickSnackBar(Context context){
        Intent intent=new Intent(context, SyncInfoActivity.class);
        context.startActivity(intent);
    }
    public static void showLocationUnableAlert(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("GPS Setting")
                .setMessage("GPS is not enabled.Please enable to proceed")
                .setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
    public static void showAllert(final Context context,final int message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        TextView myMsg = new TextView(context);
        //myMsg.setText(context.getText(R.string.attention));
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(20);
        myMsg.setTextColor(Color.RED);

        // set title
        alertDialogBuilder.setCustomTitle(myMsg);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        TextView msgTxt = (TextView) alertDialog
                .findViewById(android.R.id.message);
        msgTxt.setTextSize(20);

    }

    public static String currentDateTime(Context context, String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(calendar.getTime());
    }
    public static String getFormatedDateForAttendance(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = "";
        try {
            Date date1 = df.parse(date);
            formattedDate = df1.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return formattedDate;
    }
    public static String getFormatedDateForUdatingTask(String date){
        SimpleDateFormat df = new SimpleDateFormat("MMM dd ,yyyy");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = "";
        try {
            Date date1 = df.parse(date);
            formattedDate = df1.format(date1);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }


        return formattedDate;
    }
    public static String getFormatedDateForLastUpdatedDate(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = "";
        try {
            Date date1 = df.parse(date);
            formattedDate = df1.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return formattedDate;
    }
    public static String getFormatedDateForTaskHistory(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = "";
        try {
            Date date1 = df.parse(date);
            formattedDate = df1.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return formattedDate;
    }



    public static String serverConnection(String requestJson, String url) {
        String response = null;
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.connect();
            if (requestJson != null && (!requestJson.isEmpty())) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(requestJson);
                bufferedWriter.flush();
                bufferedWriter.close();
            }
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        httpURLConnection.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                response = sb.toString();
            } else if (responseCode == 400) {
                ResponseJson responseJson = new ResponseJson(NetworkUtils.HTTP_BAD_REQUEST, null);
                response = responseJson.toString();
            } else if (responseCode == 415) {
                ResponseJson responseJson = new ResponseJson(NetworkUtils.HTTP_UNSUPPORTED_CONTENT_TYPE, null);
                response = responseJson.toString();
            } else if (responseCode == 404) {
                ResponseJson responseJson = new ResponseJson(NetworkUtils.HTTP_PAGE_NOT_FOUND, null);
                response = responseJson.toString();
            } else if (responseCode == 503) {
                ResponseJson responseJson = new ResponseJson(NetworkUtils.HTTP_SERVER_UNAVAILABLE, null);
                response = responseJson.toString();
            } else if (responseCode == 408) {
                ResponseJson responseJson = new ResponseJson(NetworkUtils.HTTP_REQUEST_TIMEOUT, null);
                response = responseJson.toString();
            } else if (responseCode == 403) {
                ResponseJson responseJson = new ResponseJson(NetworkUtils.HTTP_FORBIDDEN, null);
                response = responseJson.toString();
            } else if (responseCode == 501) {
                ResponseJson responseJson = new ResponseJson(NetworkUtils.HTTP_NO_REQUEST_METHOD_FOUND, null);
                response = responseJson.toString();
            } else {
                ResponseJson responseJson = new ResponseJson(NetworkUtils.HTTP_ERROR, null);
                response = responseJson.toString();
            }
        } catch (MalformedURLException e) {
            ResponseJson responseJson = new ResponseJson(NetworkUtils.HTTP_MALFORMED_URL, null);
            response = responseJson.toString();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
