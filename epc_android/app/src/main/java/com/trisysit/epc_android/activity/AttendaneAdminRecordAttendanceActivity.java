package com.trisysit.epc_android.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_android.EPCApplication;
import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.SqliteDatabase.UserMaster;
import com.trisysit.epc_android.model.AttendanceModel;
import com.trisysit.epc_android.model.GetUserDataModel;
import com.trisysit.epc_android.serverModel.GetUserDetailsRequestModel;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;
import com.trisysit.epc_android.utils.LocationDetector;
import com.trisysit.epc_android.utils.PullSyncManager;
import com.trisysit.epc_android.utils.SharedPrefHelper;
import com.trisysit.epc_android.utils.SyncManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class AttendaneAdminRecordAttendanceActivity extends DrawerScreenActivity implements PullSyncManager.PullDataSyncListener {
    private EditText pin_et1, pin_et2, pin_et3, pin_et4;
    private LinearLayout punc_in_out_ll, punch_ll;
    private View rootView;
    private Toolbar toolbar;
    private boolean isGpsEnable = false;
    private boolean isNetworkEnable = false;
    private boolean doubleBackToExitPressedOnce = false;
    private TextView user_name_tv, titleTv, time_tv, date_tv, amOrPm_tv, punch_tv;
    private DataBaseManager dataBaseManager;
    private LocationManager mLocationManager;
    private AlertDialog dialog;
    private static final int ALL_5_PERMISSIONS = 100;
    LocationDetector myloc;
    private final String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    UserMaster userMaster = null;
    GetUserDataModel userDataModel = null;
    private String isPunchInPunchOut = "";
    private boolean permissionGivenForLocation = false;
    private static final int LOCATION_PERMISSION = 200;
    private static final int LOCATION_PERMISSION_FROM_SETTING = 300;
    private SharedPrefHelper sharedPrefHelper;
    private Button cancel_btn;
    private List<AttendanceModel> punchedOutableAttendane = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendane_admin_record_attendance);
        dataBaseManager = DataBaseManager.getInstance();
        sharedPrefHelper = SharedPrefHelper.getInstance(EPCApplication.getContext());
        mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        getWidgets();
        setUpNavigationDrawerForAttendAdmin(AttendaneAdminRecordAttendanceActivity.this, rootView, toolbar);
        setListener();
        setCurrentDateAndTime();
        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IS_FIRST_TIME_LOGIN).equalsIgnoreCase("true")) {
            callPullAllData();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //showDataSyncRequiredSnackBar();
    }

    private void showDataSyncRequiredSnackBar() {
        if (AppUtils.getSyncDetails(getApplicationContext())) {
            findViewById(R.id.snack_bar).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.snack_bar).setVisibility(View.GONE);
        }
    }

    private void getWidgets() {
        pin_et1 = (EditText) findViewById(R.id.pin_et1);
        pin_et2 = (EditText) findViewById(R.id.pin_et2);
        pin_et3 = (EditText) findViewById(R.id.pin_et3);
        pin_et4 = (EditText) findViewById(R.id.pin_et4);
        punc_in_out_ll = (LinearLayout) findViewById(R.id.punc_in_out_ll);
        user_name_tv = (TextView) findViewById(R.id.user_name_tv);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText(getString(R.string.pin_checkIn));
        time_tv = (TextView) findViewById(R.id.time_tv);
        date_tv = (TextView) findViewById(R.id.date_tv);
        amOrPm_tv = (TextView) findViewById(R.id.amOrPm_tv);
        rootView = findViewById(R.id.rootlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_report);
        punch_ll = (LinearLayout) findViewById(R.id.punch_ll);
        punch_tv = (TextView) findViewById(R.id.punch_tv);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
    }

    private void askForlocationPermissions() {
        if (ActivityCompat.checkSelfPermission(AttendaneAdminRecordAttendanceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            permissionGivenForLocation = true;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_FROM_SETTING);
            }
        }
    }

    private void setCurrentDateAndTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMM, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        SimpleDateFormat amOrPmformat = new SimpleDateFormat("a");
        date_tv.setText(format.format(date));
        time_tv.setText(timeFormat.format(date));
        amOrPm_tv.setText(amOrPmformat.format(date));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    TimeChangeAsyncTask timeChangeAsyncTask = new TimeChangeAsyncTask();
                    timeChangeAsyncTask.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_5_PERMISSIONS:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //Toast.makeText(RecordAttendanceActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case LOCATION_PERMISSION:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        permissionGivenForLocation = true;
                        //Toast.makeText(RecordAttendanceActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case LOCATION_PERMISSION_FROM_SETTING:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(AttendaneAdminRecordAttendanceActivity.this, "To enable the function of this application please enable location permission of the application from the setting screen of the terminal.", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    @Override
    public void onPullProgressUpdate(int percentage) {

    }

    @Override
    public void onPullDataSyncCompleted(boolean status) {
        if (status) {
            dismissProgress();
            sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IS_FIRST_TIME_LOGIN);
            askPermissions();
            getDeviceId();
        } else {
            dismissProgress();
            askPermissions();
            getDeviceId();
            Toast.makeText(AttendaneAdminRecordAttendanceActivity.this, "Sync failed", Toast.LENGTH_SHORT);
        }
    }

    public void getDeviceId() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) AttendaneAdminRecordAttendanceActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = Settings.Secure.getString(AttendaneAdminRecordAttendanceActivity.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.DEVICE_ID, deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected class TimeChangeAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Date date = new Date();
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
            SimpleDateFormat amOrPmformat = new SimpleDateFormat("a");
            String time = timeFormat.format(date);
            String amOrPm = amOrPmformat.format(date);
            String timeWithAmOrPm = time + "," + amOrPm;
            return timeWithAmOrPm;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String[] timeArray = s.split(",");
            time_tv.setText(timeArray[0]);
            amOrPm_tv.setText(timeArray[1]);
        }
    }
    private void askPermissions() {
        if ((ActivityCompat.checkSelfPermission(AttendaneAdminRecordAttendanceActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(AttendaneAdminRecordAttendanceActivity.this, permissions[1]) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(AttendaneAdminRecordAttendanceActivity.this, permissions[2]) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(AttendaneAdminRecordAttendanceActivity.this, permissions[3]) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(AttendaneAdminRecordAttendanceActivity.this, permissions[4]) != PackageManager.PERMISSION_GRANTED)) {
            //requestPermissions(permissions, ALL_5_PERMISSIONS);
            ActivityCompat.requestPermissions(AttendaneAdminRecordAttendanceActivity.this, permissions, ALL_5_PERMISSIONS);
        } else {

        }
    }

    private void captureLocation(String locationType) {
        myloc = new LocationDetector(AttendaneAdminRecordAttendanceActivity.this, locationType);
    }

    private void callAttendanceSync(String syncType) {
        SyncManager syncManager = new SyncManager(AttendaneAdminRecordAttendanceActivity.this);
        syncManager.syncAttendance();
    }

    private void callPullAllData() {
        showProgress();
        PullSyncManager pullSyncManager = new PullSyncManager(getApplicationContext(), AttendaneAdminRecordAttendanceActivity.this);
        pullSyncManager.pullAllData();
    }

    private void showProgress() {
        dialog = new SpotsDialog(AttendaneAdminRecordAttendanceActivity.this, getString(R.string.please_wait_msg), R.style.Custom);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void hideUsernameAndPunchButton() {
        pin_et1.setText("");
        pin_et2.setText("");
        pin_et3.setText("");
        pin_et4.setText("");
        user_name_tv.setVisibility(View.GONE);
        user_name_tv.setText("");
        isPunchInPunchOut = "";
        userDataModel = null;
        punchedOutableAttendane = new ArrayList<>();
        pin_et1.requestFocus();
        punc_in_out_ll.setVisibility(View.GONE);
    }

    private void setListener() {
        punch_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppUtils.isOnline(AttendaneAdminRecordAttendanceActivity.this)){
                    askForlocationPermissions();
                    if (permissionGivenForLocation) {
                        isGpsEnable = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        isNetworkEnable = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                        if (!isGpsEnable && !isNetworkEnable) {
                            AppUtils.showLocationUnableAlert(AttendaneAdminRecordAttendanceActivity.this);
                        } else {
                            if (isPunchInPunchOut.equalsIgnoreCase(AppUtils.PUNCH_IN)) {
                                String currentDate = AppUtils.currentDateTime(AttendaneAdminRecordAttendanceActivity.this, "yyyy-MM-dd");
                                String punch_in_time = AppUtils.currentDateTime(AttendaneAdminRecordAttendanceActivity.this, "yyyy-MM-dd hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE, currentDate);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME, punch_in_time);
                                showProgress();
                                captureLocation(SharedPrefHelper.INGPS);
                            } else {
                                showProgress();
                                captureLocation("outGps");
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(AttendaneAdminRecordAttendanceActivity.this, getString(R.string.no_internet_msg), Toast.LENGTH_SHORT).show();
                }

            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideUsernameAndPunchButton();

            }
        });
        pin_et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    pin_et2.requestFocus();
                }
            }
        });
        pin_et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before == 1) {
                    pin_et1.requestFocus();
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    pin_et3.requestFocus();
                }
            }
        });
        pin_et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int b = before;
                int st = start;
                int co = count;
                String ch = s.toString();
                String ch1 = ch;
                if (before == 1) {
                    pin_et2.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    pin_et4.requestFocus();
                }
            }
        });
        pin_et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before == 1) {
                    pin_et3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    String pin1 = pin_et1.getText().toString();
                    String pin2 = pin_et2.getText().toString();
                    String pin3 = pin_et3.getText().toString();
                    String pin4 = pin_et4.getText().toString();
                    if (pin1.equalsIgnoreCase("") && pin2.equalsIgnoreCase("") && pin3.equalsIgnoreCase("")) {
                        Toast.makeText(AttendaneAdminRecordAttendanceActivity.this, "Please enter pin correctly", Toast.LENGTH_SHORT).show();
                        pin_et4.setText("");
                    } else {
                        String finalPin = pin1 + pin2 + pin3 + pin4;
                        AppUtils.hideSoftKeyboard(AttendaneAdminRecordAttendanceActivity.this);
                        if (AppUtils.isOnline(AttendaneAdminRecordAttendanceActivity.this)) {
                            showProgress();
                            GetUserDetailsRequestModel model = new GetUserDetailsRequestModel();
                            model.setUserPin(finalPin);
                            SyncManager manager = new SyncManager(AttendaneAdminRecordAttendanceActivity.this);
                            manager.syncGetUserDetils(model);
                        } else {
                            Toast.makeText(AttendaneAdminRecordAttendanceActivity.this, getString(R.string.no_internet_msg), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }

    private void dismissProgress() {
        if (dialog != null) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void disMissProgress(GetUserDataModel dataModel, String message) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                if (dataModel != null) {
                    userDataModel = dataModel;
                    String userName = dataModel.getFirstName() + " " + dataModel.getLastName();
                    user_name_tv.setVisibility(View.VISIBLE);
                    punc_in_out_ll.setVisibility(View.VISIBLE);
                    user_name_tv.setText(userName);
                    List<AttendanceModel> attendanceModels = dataModel.getAttendanceList();
                    if (attendanceModels.size() > 0) {
                        for (int i = 0; i < attendanceModels.size(); i++) {
                            if (attendanceModels.get(i).getOutTime().equalsIgnoreCase("")) {
                                isPunchInPunchOut = AppUtils.PUNCH_OUT;
                                punchedOutableAttendane.add(attendanceModels.get(i));
                                break;
                            } else {
                                isPunchInPunchOut = AppUtils.PUNCH_IN;
                            }
                        }
                    } else {
                        isPunchInPunchOut = AppUtils.PUNCH_IN;
                    }
                } else {
                    pin_et1.setText("");
                    pin_et2.setText("");
                    pin_et3.setText("");
                    pin_et4.setText("");
                    pin_et1.requestFocus();
                    user_name_tv.setVisibility(View.GONE);
                    punc_in_out_ll.setVisibility(View.GONE);
                    userDataModel = null;
                    user_name_tv.setText("");
                    isPunchInPunchOut = "";
                    punchedOutableAttendane = new ArrayList<>();
                    Toast.makeText(AttendaneAdminRecordAttendanceActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        }

    }

    public void saveIntoDb(String location, String type, String syncType) {
        if (type.equalsIgnoreCase(AppUtils.PUNCH_IN)) {
            String mobileId = UUID.randomUUID().toString();
            AttendanceModel model = new AttendanceModel();
            model.setMobileId(mobileId);
            model.setInTime(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME));
            model.setDate(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE));
            model.setOutTime("");
            model.setOutGPS("");
            model.setDeviceId(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.DEVICE_ID));
            model.setStatus(AppUtils.IN_PROGRESS);
            model.setInGPS(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.INGPS));
            model.setName(userDataModel.getFirstName() + " " + userDataModel.getLastName());
            model.setCreatedBy(userDataModel.getUserId());
            model.setCreatedDate(AppUtils.currentDateTime(AttendaneAdminRecordAttendanceActivity.this, "yyyy-MM-dd HH:mm:ss"));
            DatabaaseUtils.insertAttendance(AttendaneAdminRecordAttendanceActivity.this, model);
            isPunchInPunchOut = AppUtils.PUNCH_OUT;
            Toast.makeText(AttendaneAdminRecordAttendanceActivity.this, "Attendance recorded successfully", Toast.LENGTH_SHORT).show();
            hideUsernameAndPunchButton();

        } else if (type.equalsIgnoreCase(AppUtils.PUNCH_OUT)) {
            String userName = userDataModel.getFirstName() + " " + userDataModel.getLastName();
            if (punchedOutableAttendane.size() > 0) {
                AttendanceModel model = punchedOutableAttendane.get(0);
                String outTime = AppUtils.currentDateTime(AttendaneAdminRecordAttendanceActivity.this, "yyyy-MM-dd hh:mm a");
                model.setOutTime(outTime);
                model.setOutGPS(location);
                model.setId(model.getId());
                model.setDeviceId(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.DEVICE_ID));
                model.setCreatedDate(AppUtils.currentDateTime(AttendaneAdminRecordAttendanceActivity.this, "yyyy-MM-dd HH:mm:ss"));
                model.setStatus(AppUtils.IN_PROGRESS);
                model.setName(userDataModel.getFirstName() + " " + userDataModel.getLastName());
                String currentDate = AppUtils.currentDateTime(AttendaneAdminRecordAttendanceActivity.this, "yyyy-MM-dd");
                model.setDate(currentDate);
                model.setCreatedBy(userDataModel.getUserId());
                DatabaaseUtils.replaceAttendance(AttendaneAdminRecordAttendanceActivity.this, model);
                isPunchInPunchOut = AppUtils.PUNCH_IN;
                Toast.makeText(AttendaneAdminRecordAttendanceActivity.this, "Attendance recorded successfully", Toast.LENGTH_SHORT).show();
                hideUsernameAndPunchButton();
            }

        }
        if (AppUtils.isOnline(AttendaneAdminRecordAttendanceActivity.this)) {
            callAttendanceSync(syncType);
        } else {
            showDataSyncRequiredSnackBar();
        }
        dismissProgress();
    }
}
