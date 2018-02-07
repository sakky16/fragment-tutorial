package com.trisysit.epc_android.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.trisysit.epc_android.BuildConfig;
import com.trisysit.epc_android.EPCApplication;
import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.SqliteDatabase.ImageSync;
import com.trisysit.epc_android.fragments.ImageDetailsDialogFragment;
import com.trisysit.epc_android.model.AttendanceModel;
import com.trisysit.epc_android.model.ImageFIleModel;
import com.trisysit.epc_android.model.LoginDataModel;
import com.trisysit.epc_android.network.SyncApiClass;
import com.trisysit.epc_android.serverModel.AttendanceDataModel;
import com.trisysit.epc_android.serverModel.AttendanceRequest;
import com.trisysit.epc_android.serverModel.AttendanceResponse;
import com.trisysit.epc_android.serverModel.LoginRequestModel;
import com.trisysit.epc_android.serverModel.LoginResponseModel;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;
import com.trisysit.epc_android.utils.LocationDetector;
import com.trisysit.epc_android.utils.NetworkUtils;
import com.trisysit.epc_android.utils.PullSyncManager;
import com.trisysit.epc_android.utils.SharedPrefHelper;
import com.trisysit.epc_android.utils.SyncManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecordAttendanceActivity extends DrawerScreenActivity implements PullSyncManager.PullDataSyncListener {
    private View rootView;
    private Toolbar toolbar;
    private final String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private TextView titleTv, time_tv, date_tv, amOrPm_tv,
            punch_tv, image_count1, image_count2, image_count3,
            image_count4, image_count5, image_count6;
    private boolean isPermissionGivenForCamera=false;
    private static final int ALL_5_PERMISSIONS = 100;
    private static final int LOCATION_PERMISSION = 200;
    private static final int LOCATION_PERMISSION_FROM_SETTING=300;
    private static final int CAMERA_IMAGE_REQUEST1 = 1001;
    private static final int CAMERA_IMAGE_REQUEST2 = 1002;
    private static final int CAMERA_IMAGE_REQUEST3 = 1003;
    private static final int CAMERA_IMAGE_REQUEST4 = 1004;
    private static final int CAMERA_IMAGE_REQUEST5 = 1005;
    private static final int CAMERA_IMAGE_REQUEST6 = 1006;
    private static final int CAMERA_IMAGE_REQUEST7 = 1007;
    private String workImageUrl1 = "", workImageUrl2 = "",
            workImageUrl3 = "", workImageUrl4 = "", workImageUrl5 = "", workImageUrl6 = "",
            workImageUrl7 = "";
    private ImageView image_iv1, image_iv2, image_iv3, image_iv4, image_iv5, image_iv6,
            image_iv7;
    private boolean isGpsEnable = false;
    private boolean isNetworkEnable = false;
    private LinearLayout punch_ll, commentImage_ll;
    private SharedPrefHelper sharedPrefHelper;
    private EditText comment_et;
    private LocationManager mLocationManager;
    private Button save_btn;
    private LinearLayout image1_ll, image_count_ll;
    private AlertDialog dialog;
    LocationDetector myloc;
    String carrierName = "";
    private ScrollView scroll_view;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean permissionGivenForLocation=false;
    private boolean cameraImage=false;
    private boolean capturedImages=false;
    String currentDate = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd");
    private boolean isImage1Sync=false,isImage2Sync=false,isImage3Sync=false,isImage4Sync=false,isImage5Sync=false,isImage6Sync=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_attendance_layout);
        getWidgets();
        sharedPrefHelper = SharedPrefHelper.getInstance(EPCApplication.getContext());
        mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        setData();
        TelephonyManager tManager = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        carrierName = tManager.getNetworkOperatorName();
        setCurrentDateAndTime();
        setListener();
        setUpNavigationDrawer(RecordAttendanceActivity.this, rootView, toolbar);
        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IS_FIRST_TIME_LOGIN).equalsIgnoreCase("true")) {
            callPullAllData();
        }
        String currentDate = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd");
        //if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE).equalsIgnoreCase(currentDate)) {
            showWorkImage();
        //}
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        SyncDataSyncTask syncDataSyncTask=new SyncDataSyncTask();
        syncDataSyncTask.execute();

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
        showDataSyncRequiredSnackBar();
        SyncData syncData=new SyncData();
        syncData.callLoginApi(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.USERNAME),sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.ENCODED_PASSWORD));

       // syncData.pullAttendance();



    }

    private void showDataSyncRequiredSnackBar() {
        if (AppUtils.getSyncDetails(getApplicationContext())) {
            findViewById(R.id.snack_bar).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.snack_bar).setVisibility(View.GONE);
        }
    }

    private void getWidgets() {
        rootView = findViewById(R.id.rootlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_report);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        time_tv = (TextView) findViewById(R.id.time_tv);
        date_tv = (TextView) findViewById(R.id.date_tv);
        amOrPm_tv = (TextView) findViewById(R.id.amOrPm_tv);
        setSupportActionBar(toolbar);
        scroll_view = (ScrollView) findViewById(R.id.scorll_view);
        image_iv1 = (ImageView) findViewById(R.id.image1);
        image_iv2 = (ImageView) findViewById(R.id.image2);
        image_iv3 = (ImageView) findViewById(R.id.image3);
        image_iv4 = (ImageView) findViewById(R.id.image4);
        image_iv5 = (ImageView) findViewById(R.id.image5);
        image_iv6 = (ImageView) findViewById(R.id.image6);
        image_iv7 = (ImageView) findViewById(R.id.image7);
        image1_ll = (LinearLayout) findViewById(R.id.images_ll1);
        image_count_ll = (LinearLayout) findViewById(R.id.image_count_ll);
        image_count1 = (TextView) findViewById(R.id.imageCount1);
        image_count2 = (TextView) findViewById(R.id.imageCount2);
        image_count3 = (TextView) findViewById(R.id.imageCount3);
        image_count4 = (TextView) findViewById(R.id.imageCount4);
        image_count5 = (TextView) findViewById(R.id.imageCount5);
        image_count6 = (TextView) findViewById(R.id.imageCount6);
        punch_tv = (TextView) findViewById(R.id.punch_tv);
        punch_ll = (LinearLayout) findViewById(R.id.punch_ll);
        save_btn = (Button) findViewById(R.id.save_btn);
        commentImage_ll = (LinearLayout) findViewById(R.id.commentImage_ll);
        comment_et = (EditText) findViewById(R.id.comment_et);
    }

    private void showWorkImage() {
        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("")) {
            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("no_image")) {
                image_iv1.setVisibility(View.VISIBLE);
                image_iv1.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                image_iv2.setVisibility(View.VISIBLE);
            } else {
                String[] imageArray = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).split(",");
                int moreImageCount = imageArray.length - 1;
                if (moreImageCount > 0) {
                    image_count_ll.setVisibility(View.VISIBLE);
                    image_count1.setVisibility(View.VISIBLE);
                    image_count1.setText("+ " + moreImageCount + " more");
                }
                showGlideImage(imageArray[0], image_iv1);
                image_iv2.setVisibility(View.VISIBLE);
            }
        }

        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("")) {
            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("no_image")) {
                image_iv2.setVisibility(View.VISIBLE);
                image_iv2.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                image_iv3.setVisibility(View.VISIBLE);
            } else {
                image_iv2.setVisibility(View.VISIBLE);
                String[] imageArray = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).split(",");
                int moreImageCount = imageArray.length - 1;
                if (moreImageCount > 0) {
                    image_count_ll.setVisibility(View.VISIBLE);
                    image_count2.setVisibility(View.VISIBLE);
                    image_count2.setText("+ " + moreImageCount + " more");
                }
                showGlideImage(imageArray[0], image_iv2);
                image_iv3.setVisibility(View.VISIBLE);
            }

        }
        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("")) {
            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("no_image")) {
                image_iv3.setVisibility(View.VISIBLE);
                image_iv3.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                image_iv4.setVisibility(View.VISIBLE);
            } else {
                image_iv3.setVisibility(View.VISIBLE);
                String[] imageArray = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).split(",");
                int moreImageCount = imageArray.length - 1;
                if (moreImageCount > 0) {
                    image_count_ll.setVisibility(View.VISIBLE);
                    image_count3.setVisibility(View.VISIBLE);
                    image_count3.setText("+ " + moreImageCount + " more");
                }
                showGlideImage(imageArray[0], image_iv3);
                image_iv4.setVisibility(View.VISIBLE);
            }

        }
        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("")) {
            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("no_image")) {
                image_iv4.setVisibility(View.VISIBLE);
                image_iv4.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                image_iv5.setVisibility(View.VISIBLE);
            } else {
                image_iv4.setVisibility(View.VISIBLE);
                String[] imageArray = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).split(",");
                int moreImageCount = imageArray.length - 1;
                if (moreImageCount > 0) {
                    image_count_ll.setVisibility(View.VISIBLE);
                    image_count4.setVisibility(View.VISIBLE);
                    image_count4.setText("+ " + moreImageCount + " more");
                }
                showGlideImage(imageArray[0], image_iv4);
                image_iv5.setVisibility(View.VISIBLE);
            }

        }
        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("")) {
            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("no_image")) {
                image_iv5.setVisibility(View.VISIBLE);
                image_iv5.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                image_iv6.setVisibility(View.VISIBLE);
            } else {
                image_iv5.setVisibility(View.VISIBLE);
                String[] imageArray = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).split(",");
                int moreImageCount = imageArray.length - 1;
                if (moreImageCount > 0) {
                    image_count_ll.setVisibility(View.VISIBLE);
                    image_count5.setVisibility(View.VISIBLE);
                    image_count5.setText("+ " + moreImageCount + " more");
                }
                showGlideImage(imageArray[0], image_iv5);
                image_iv6.setVisibility(View.VISIBLE);
            }

        }
        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("")) {
            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("no_image")) {
                image_iv6.setVisibility(View.VISIBLE);
                image_iv6.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            } else {
                image_iv6.setVisibility(View.VISIBLE);
                String[] imageArray = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).split(",");
                int moreImageCount = imageArray.length - 1;
                if (moreImageCount > 0) {
                    image_count_ll.setVisibility(View.VISIBLE);
                    image_count6.setVisibility(View.VISIBLE);
                    image_count6.setText("+ " + moreImageCount + " more");
                }
                showGlideImage(imageArray[0], image_iv6);
            }
        }
    }

    private void setListener() {
        punch_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForlocationPermissions();
                if (permissionGivenForLocation) {
                    isGpsEnable = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    isNetworkEnable = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    if (!isGpsEnable && !isNetworkEnable) {
                        AppUtils.showLocationUnableAlert(RecordAttendanceActivity.this);
                    } else {
                        if (punch_tv.getText().toString().equalsIgnoreCase(getString(R.string.punch_in))) {
                            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE).equalsIgnoreCase("")) {
                                String currentDate = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd");
                                String punch_in_time = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE, currentDate);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME, punch_in_time);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_FIRST_TIME_PUNCH_IN, "true");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "1");
                                showProgress();
                                setData();

                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_OUT, "true");
                                captureLocation(SharedPrefHelper.INGPS);
                            } else {
                                String currentDate = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd");
                                if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE).equalsIgnoreCase(currentDate) && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IS_USER_MULTIPLE_ATT).equalsIgnoreCase("YES")) {
                                    String punch_in_time = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd hh:mm a");
                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE, currentDate);
                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME, punch_in_time);
                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_FIRST_TIME_PUNCH_IN, "true");
                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_IN,"true");
                                    showProgress();
                                    setData();

                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_OUT, "true");
                                    captureLocation(SharedPrefHelper.INGPS);

                                    //sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "1");

                                    } else {
                                    String punch_in_time = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd hh:mm a");
                                    sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE);
                                    sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME);
                                    sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.COUNTER);
                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE, currentDate);
                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME, punch_in_time);
                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_FIRST_TIME_PUNCH_IN, "true");
                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "1");
                                    DataBaseManager.getInstance().deleteAllImages();
                                    DataBaseManager.getInstance().clearImageTable();
                                    removeFromSharedPrefForFirstTimeLogin();
                                    Toast.makeText(RecordAttendanceActivity.this,"This user is not allowed to take Multiple Attendance",Toast.LENGTH_SHORT).show();
                                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_IN,"false");
                                }
                            }
                           // sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_IN, "true");
                            //commentImage_ll.setVisibility(View.VISIBLE);


                           // punch_tv.setText(getString(R.string.punch_out));
                            //comment_et.setFocusable(true);
                           // AppUtils.showAllert(RecordAttendanceActivity.this, R.string.punch_in_success_msg);
                        } else {
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_OUT, "true");
                            showProgress();
                            if (AppUtils.isOnline(RecordAttendanceActivity.this)) {
                                captureLocation("outGps");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_IN,"false");
                            } else if (!carrierName.equalsIgnoreCase("")) {
                                captureLocation("outGps");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_IN,"false");

                            } else {
                                captureLocation("outGps");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_IN,"false");

                                //saveIntoDb("0.0,0.0", AppUtils.PUNCH_OUT, "outGps");
                            }
                        }
                    }
                }
            }

        });
        comment_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollUpScrollView();
            }
        });
        comment_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scrollUpScrollView();
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGpsEnable = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnable = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (!isGpsEnable && !isNetworkEnable) {
                    AppUtils.showLocationUnableAlert(RecordAttendanceActivity.this);
                } else {
                    String comment = comment_et.getText().toString();
                    if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("1")) {
                        if (AppUtils.isEmpty(comment)) {
                            if (AppUtils.isEmpty(workImageUrl1)) {
                                Toast.makeText(RecordAttendanceActivity.this, "Please write comment or upload photos for your work", Toast.LENGTH_SHORT).show();
                            } else {
                                String imageTime1 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME1, imageTime1);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE1, workImageUrl1);
                                comment_et.setText("");
                                image_iv2.setVisibility(View.VISIBLE);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "2");
                                saveDataInSaveButton(SharedPrefHelper.GPS1);
                            }
                        } else {
                            if ((AppUtils.isEmpty(workImageUrl1))) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMENT1, comment);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE1, "no_image");
                                comment_et.setText("");
                                String imageTime1 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME1, imageTime1);
                                image_iv1.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                                image_iv2.setVisibility(View.VISIBLE);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "2");
                                saveDataInSaveButton(SharedPrefHelper.GPS1);
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE1, workImageUrl1);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMENT1, comment);
                                image_iv2.setVisibility(View.VISIBLE);
                                String imageTime1 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME1, imageTime1);
                                comment_et.setText("");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "2");
                                saveDataInSaveButton(SharedPrefHelper.GPS1);
                            }

                        }
                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("2")) {
                        if (AppUtils.isEmpty(comment)) {
                            if (AppUtils.isEmpty(workImageUrl2)) {
                                Toast.makeText(RecordAttendanceActivity.this, "Please write comment or upload photo for your work", Toast.LENGTH_SHORT).show();
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE2, workImageUrl2);
                                comment_et.setText("");
                                String imageTime2 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME2, imageTime2);
                                image_iv3.setVisibility(View.VISIBLE);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "3");
                                saveDataInSaveButton(SharedPrefHelper.GPS2);
                            }
                        } else {
                            if (AppUtils.isEmpty(workImageUrl2)) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT2, comment);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE2, "no_image");
                                comment_et.setText("");
                                image_iv2.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                                image_iv3.setVisibility(View.VISIBLE);
                                String imageTime2 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME2, imageTime2);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "3");
                                saveDataInSaveButton(SharedPrefHelper.GPS2);
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE2, workImageUrl2);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT2, comment);
                                image_iv3.setVisibility(View.VISIBLE);
                                String imageTime2 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME2, imageTime2);
                                comment_et.setText("");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "3");
                                saveDataInSaveButton(SharedPrefHelper.GPS2);
                            }
                        }
                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("3")) {
                        if (AppUtils.isEmpty(comment)) {
                            if (AppUtils.isEmpty(workImageUrl3)) {
                                Toast.makeText(RecordAttendanceActivity.this, "Please write comment or upload photo for your work", Toast.LENGTH_SHORT).show();
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE3, workImageUrl3);
                                comment_et.setText("");
                                String imageTime3 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME3, imageTime3);
                                image_iv4.setVisibility(View.VISIBLE);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "4");
                                saveDataInSaveButton(SharedPrefHelper.GPS3);
                            }
                        } else {
                            if (AppUtils.isEmpty(workImageUrl3)) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT3, comment);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE3, "no_image");
                                comment_et.setText("");
                                image_iv3.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                                image_iv4.setVisibility(View.VISIBLE);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "4");
                                String imageTime3 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME3, imageTime3);
                                saveDataInSaveButton(SharedPrefHelper.GPS3);
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE3, workImageUrl3);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT3, comment);
                                image_iv4.setVisibility(View.VISIBLE);
                                comment_et.setText("");
                                String imageTime3 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME3, imageTime3);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "4");
                                saveDataInSaveButton(SharedPrefHelper.GPS3);
                            }
                        }
                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("4")) {
                        if (AppUtils.isEmpty(comment)) {
                            if (AppUtils.isEmpty(workImageUrl4)) {
                                Toast.makeText(RecordAttendanceActivity.this, "Please write comment or upload photo for your work", Toast.LENGTH_SHORT).show();
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE4, workImageUrl4);
                                comment_et.setText("");
                                String imageTime4 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME4, imageTime4);
                                image_iv5.setVisibility(View.VISIBLE);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "5");
                                saveDataInSaveButton(SharedPrefHelper.GPS4);
                            }
                        } else {
                            if (AppUtils.isEmpty(workImageUrl4)) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT4, comment);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE4, "no_image");
                                comment_et.setText("");
                                String imageTime4 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME4, imageTime4);
                                image_iv4.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                                image_iv5.setVisibility(View.VISIBLE);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "5");
                                saveDataInSaveButton(SharedPrefHelper.GPS4);
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE4, workImageUrl4);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT4, comment);
                                image_iv5.setVisibility(View.VISIBLE);
                                String imageTime4 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME4, imageTime4);
                                comment_et.setText("");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "5");
                                saveDataInSaveButton(SharedPrefHelper.GPS4);
                            }
                        }
                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("5")) {
                        if (AppUtils.isEmpty(comment)) {
                            if (AppUtils.isEmpty(workImageUrl5)) {
                                Toast.makeText(RecordAttendanceActivity.this, "Please write comment or upload photo for your work", Toast.LENGTH_SHORT).show();
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE5, workImageUrl5);
                                comment_et.setText("");
                                String imageTime5 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME5, imageTime5);
                                image_iv6.setVisibility(View.VISIBLE);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "6");
                                saveDataInSaveButton(SharedPrefHelper.GPS5);
                            }
                        } else {
                            if (AppUtils.isEmpty(workImageUrl5)) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT5, comment);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE5, "no_image");
                                comment_et.setText("");
                                image_iv5.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                                image_iv6.setVisibility(View.VISIBLE);
                                String imageTime5 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME5, imageTime5);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "6");
                                saveDataInSaveButton(SharedPrefHelper.GPS5);
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE5, workImageUrl5);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT5, comment);
                                String imageTime5 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME5, imageTime5);
                                image_iv6.setVisibility(View.VISIBLE);
                                comment_et.setText("");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "6");
                                saveDataInSaveButton(SharedPrefHelper.GPS5);
                            }
                        }
                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("6")) {
                        if (AppUtils.isEmpty(comment)) {
                            if (AppUtils.isEmpty(workImageUrl6)) {
                                Toast.makeText(RecordAttendanceActivity.this, "Please write comment or upload photo for your work", Toast.LENGTH_SHORT).show();
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE6, workImageUrl6);
                                comment_et.setText("");
                                String imageTime6 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME6, imageTime6);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "7");
                                saveDataInSaveButton(SharedPrefHelper.GPS6);
                            }
                        } else {
                            if (AppUtils.isEmpty(workImageUrl6)) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT6, comment);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE6, "no_image");
                                comment_et.setText("");
                                String imageTime6 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME6, imageTime6);
                                image_iv6.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "7");
                                saveDataInSaveButton(SharedPrefHelper.GPS6);
                            } else {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE6, workImageUrl6);
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT6, comment);
                                String imageTime6 = AppUtils.currentDateTime(RecordAttendanceActivity.this, "hh:mm a");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME6, imageTime6);
                                comment_et.setText("");
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "7");
                                saveDataInSaveButton(SharedPrefHelper.GPS6);
                            }
                        }
                        image1_ll.setVisibility(View.GONE);
                        image_iv7.setVisibility(View.INVISIBLE);

                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("7")) {

                        Toast.makeText(RecordAttendanceActivity.this, "You cannt record your work more than 6 times", Toast.LENGTH_SHORT).show();
                    }
                    AppUtils.hideSoftKeyboard(RecordAttendanceActivity.this);
                }
            }
        });
        image_iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("")) {
                    if (workImageUrl1.equalsIgnoreCase("")) {
                        askPermissions();
                        if(isPermissionGivenForCamera){
                            setIntentForCaptureImage("image1");
                            cameraImage=true;

                        }

                    } else {
                        showImageDialogCapture(workImageUrl1, sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMENT1));
                    }

                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("no_image")) {
                    showImageDialog("", sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMENT1));
                } else {
                    showImageDialog(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1), sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMENT1));
                }

            }
        });
        image_iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("")) {
                    if (workImageUrl2.equalsIgnoreCase("")) {
                        askPermissions();
                        if(isPermissionGivenForCamera){
                            setIntentForCaptureImage("image2");
                            cameraImage=true;

                        }
                    } else {
                        showImageDialogCapture(workImageUrl2, sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT2));
                    }

                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("no_image")) {
                    showImageDialog("", sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT2));
                } else {
                    showImageDialog(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2), sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT2));
                }
            }
        });
        image_iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("")) {
                    if (workImageUrl3.equalsIgnoreCase("")) {
                        askPermissions();
                        if(isPermissionGivenForCamera){
                            setIntentForCaptureImage("image3");
                            cameraImage=true;

                        }
                    } else {
                        showImageDialogCapture(workImageUrl3, sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT3));
                    }
                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("no_image")) {
                    showImageDialog("", sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT3));
                } else {
                    showImageDialog(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3), sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT3));
                }
            }
        });
        image_iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("")) {
                    if (workImageUrl4.equalsIgnoreCase("")) {
                        askPermissions();
                        if(isPermissionGivenForCamera){
                            setIntentForCaptureImage("image4");
                            cameraImage=true;

                        }
                    } else {
                        showImageDialogCapture(workImageUrl4, sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT4));
                    }

                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("no_image")) {
                    showImageDialog("", sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT4));
                } else {
                    showImageDialog(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4), sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT4));
                }
            }
        });
        image_iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("")) {
                    if (workImageUrl5.equalsIgnoreCase("")) {
                        askPermissions();
                        if(isPermissionGivenForCamera){
                            setIntentForCaptureImage("image5");
                            cameraImage=true;

                        }
                    } else {
                        showImageDialogCapture(workImageUrl5, sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT5));
                    }

                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("no_image")) {
                    showImageDialog("", sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT5));
                } else {
                    showImageDialog(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5), sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT5));
                }
            }
        });
        image_iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("")) {
                    if (workImageUrl6.equalsIgnoreCase("")) {
                        askPermissions();
                        if(isPermissionGivenForCamera){
                            setIntentForCaptureImage("image6");
                            cameraImage=true;

                        }
                    } else {
                        showImageDialogCapture(workImageUrl6, sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT6));
                    }

                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("no_image")) {
                    showImageDialog("", sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT6));
                } else {
                    showImageDialog(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6), sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT6));
                }
            }
        });
        image_iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workImageUrl7.equalsIgnoreCase("")) {
                    askPermissions();
                    if(isPermissionGivenForCamera){
                        setIntentForCaptureImage("image7");
                        cameraImage=true;

                    }

                }
            }
        });

    }

    private void saveDataInSaveButton(String gpsType) {
        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_OUT, "true");
        showSavingProgress();
        if (AppUtils.isOnline(RecordAttendanceActivity.this)) {
            captureLocation(gpsType);
        } else if (!carrierName.equalsIgnoreCase("")) {
            captureLocation(gpsType);
        } else {
            captureLocation(gpsType);
        }
    }

    private void scrollUpScrollView() {
        scroll_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                View lastChild = scroll_view.getChildAt(scroll_view.getChildCount() - 1);
                int bottom = lastChild.getBottom() + scroll_view.getPaddingBottom();
                int sy = scroll_view.getScrollY();
                int sh = scroll_view.getHeight();
                int delta = bottom - (sy + sh);
                scroll_view.smoothScrollBy(0, delta);

            }
        }, 200);
    }

    private void removeFromSharedPrefForFirstTimeLogin() {
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IS_USER_MULTIPLE_ATT);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE1);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE2);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE3);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE4);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE5);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE6);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.COMENT1);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.COMMENT2);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.COMMENT3);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.COMMENT4);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.COMMENT5);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.COMMENT6);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE_TIME1);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE_TIME2);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE_TIME3);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE_TIME4);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE_TIME5);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IMAGE_TIME6);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.GPS1);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.GPS2);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.GPS3);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.GPS4);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.GPS5);
        sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.GPS6);
        image_iv1.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
        image_iv2.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
        image_iv3.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
        image_iv4.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
        image_iv5.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
        image_iv6.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
        image_iv2.setVisibility(View.INVISIBLE);
        image_iv3.setVisibility(View.INVISIBLE);
        image_iv4.setVisibility(View.INVISIBLE);
        image_iv5.setVisibility(View.INVISIBLE);
        image_iv6.setVisibility(View.INVISIBLE);
        image_count_ll.setVisibility(View.GONE);
    }

    private void showImageDialog(String imageUrl, String comment) {
        ImageDetailsDialogFragment fragment = new ImageDetailsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", imageUrl);
        bundle.putString("comment", comment);
        bundle.putString("fromPage",AppUtils.RECORD_ATTEN_PAGE_AFTER_SYN);
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), "imag1");
    }
    private void showImageDialogCapture(String imageUrl, String comment) {
        ImageDetailsDialogFragment fragment = new ImageDetailsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", imageUrl);
        bundle.putString("comment", comment);
        bundle.putString("fromPage", AppUtils.RECORD_ATTEN_PAGE);
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), "imag1");
    }

    private void setIntentForCaptureImage(String imageType) {
        String imageFolderPath = Environment.getExternalStorageDirectory().toString()
                + "/productImage";
        File imagesFolder = new File(imageFolderPath);
        imagesFolder.mkdirs();

        // Generating file name
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
        File imageFile = new File(imageFolderPath, imageName);
        if (imageType.equalsIgnoreCase("image1")) {
            workImageUrl1 = imageFile.getAbsolutePath();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(takePictureIntent,
                    CAMERA_IMAGE_REQUEST1);
        } else if (imageType.equalsIgnoreCase("image4")) {
            workImageUrl4 = imageFile.getAbsolutePath();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(takePictureIntent,
                    CAMERA_IMAGE_REQUEST4);
        } else if (imageType.equalsIgnoreCase("image2")) {
            workImageUrl2 = imageFile.getAbsolutePath();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(takePictureIntent,
                    CAMERA_IMAGE_REQUEST2);
        } else if (imageType.equalsIgnoreCase("image3")) {
            workImageUrl3 = imageFile.getAbsolutePath();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(takePictureIntent,
                    CAMERA_IMAGE_REQUEST3);
        } else if (imageType.equalsIgnoreCase("image5")) {
            workImageUrl5 = imageFile.getAbsolutePath();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(takePictureIntent,
                    CAMERA_IMAGE_REQUEST5);
        } else if (imageType.equalsIgnoreCase("image6")) {
            workImageUrl6 = imageFile.getAbsolutePath();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(takePictureIntent,
                    CAMERA_IMAGE_REQUEST6);
        } else if (imageType.equalsIgnoreCase("image7")) {
            workImageUrl7 = imageFile.getAbsolutePath();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(takePictureIntent,
                    CAMERA_IMAGE_REQUEST7);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_IMAGE_REQUEST1) {
            if (resultCode == RESULT_OK) {
                showImage(workImageUrl1, image_iv1);
            } else if (resultCode == RESULT_CANCELED) {
                workImageUrl1 = "";
            }
        } else if (requestCode == CAMERA_IMAGE_REQUEST2) {
            if (resultCode == RESULT_OK) {
                showImage(workImageUrl2, image_iv2);
            } else if (resultCode == RESULT_CANCELED) {
                workImageUrl2 = "";
            }
        } else if (requestCode == CAMERA_IMAGE_REQUEST3) {
            if (resultCode == RESULT_OK) {
                showImage(workImageUrl3, image_iv3);
            } else if (resultCode == RESULT_CANCELED) {
                workImageUrl3 = "";
            }
        } else if (requestCode == CAMERA_IMAGE_REQUEST4) {
            if (resultCode == RESULT_OK) {
                showImage(workImageUrl4, image_iv4);
            } else if (resultCode == RESULT_CANCELED) {
                workImageUrl4 = "";
            }
        } else if (requestCode == CAMERA_IMAGE_REQUEST5) {
            if (resultCode == RESULT_OK) {
                showImage(workImageUrl5, image_iv5);
            } else if (resultCode == RESULT_CANCELED) {
                workImageUrl5 = "";
            }
        } else if (requestCode == CAMERA_IMAGE_REQUEST6) {
            if (resultCode == RESULT_OK) {
                showImage(workImageUrl6, image_iv6);
            } else {
                workImageUrl6 = "";
            }
        } else if (requestCode == CAMERA_IMAGE_REQUEST7) {
            if (resultCode == RESULT_OK) {
                showImage(workImageUrl7, image_iv7);
            } else {
                workImageUrl7 = "";
            }
        }
    }

    private void showMoreImageCount(int moreImageCOunt, TextView textView) {
        if (moreImageCOunt > 0) {
            image_count_ll.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.setText("+ " + moreImageCOunt + " more");
        }

    }

    public void showImage(String imageUrl, ImageView imageView) {
        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("1")) {
            if (imageUrl.equalsIgnoreCase(workImageUrl1)) {
                image_iv2.setVisibility(View.VISIBLE);
                showGlideImage(workImageUrl1, image_iv1);
            } else if (imageUrl.equalsIgnoreCase(workImageUrl2)) {
                workImageUrl1 = workImageUrl1 + "," + workImageUrl2;
                String imageArray[] = workImageUrl1.split(",");
                int moreImageCount = imageArray.length - 1;
                showMoreImageCount(moreImageCount, image_count1);
                showGlideImage(imageArray[moreImageCount], image_iv1);
                image_iv2.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
                workImageUrl2 = "";
            }
        } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("2")) {
            if (imageUrl.equalsIgnoreCase(workImageUrl2)) {
                image_iv3.setVisibility(View.VISIBLE);
                showGlideImage(workImageUrl2, image_iv2);
            } else if (imageUrl.equalsIgnoreCase(workImageUrl3)) {
                workImageUrl2 = workImageUrl2 + "," + workImageUrl3;
                String imageArray[] = workImageUrl2.split(",");
                int moreImageCount = imageArray.length - 1;
                showMoreImageCount(moreImageCount, image_count2);
                showGlideImage(imageArray[moreImageCount], image_iv2);
                image_iv3.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
                workImageUrl3 = "";
            }
        } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("3")) {
            if (imageUrl.equalsIgnoreCase(workImageUrl3)) {
                image_iv4.setVisibility(View.VISIBLE);
                showGlideImage(workImageUrl3, image_iv3);
            } else if (imageUrl.equalsIgnoreCase(workImageUrl4)) {
                workImageUrl3 = workImageUrl3 + "," + workImageUrl4;
                String imageArray[] = workImageUrl3.split(",");
                int moreImageCount = imageArray.length - 1;
                showMoreImageCount(moreImageCount, image_count3);
                showGlideImage(imageArray[moreImageCount], image_iv3);
                image_iv4.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
                workImageUrl4 = "";
            }
        } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("4")) {
            if (imageUrl.equalsIgnoreCase(workImageUrl4)) {
                image_iv5.setVisibility(View.VISIBLE);
                showGlideImage(workImageUrl4, image_iv4);
            } else if (imageUrl.equalsIgnoreCase(workImageUrl5)) {
                workImageUrl4 = workImageUrl4 + "," + workImageUrl5;
                String imageArray[] = workImageUrl4.split(",");
                int moreImageCount = imageArray.length - 1;
                showMoreImageCount(moreImageCount, image_count4);
                showGlideImage(imageArray[moreImageCount], image_iv4);
                image_iv5.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
                workImageUrl5 = "";
            }
        } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("5")) {
            if (imageUrl.equalsIgnoreCase(workImageUrl5)) {
                image_iv6.setVisibility(View.VISIBLE);
                showGlideImage(workImageUrl5, image_iv5);
            } else if (imageUrl.equalsIgnoreCase(workImageUrl6)) {
                workImageUrl5 = workImageUrl5 + "," + workImageUrl6;
                String imageArray[] = workImageUrl5.split(",");
                int moreImageCount = imageArray.length - 1;
                showMoreImageCount(moreImageCount, image_count5);
                showGlideImage(imageArray[moreImageCount], image_iv5);
                image_iv6.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
                workImageUrl6 = "";
            }
        } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COUNTER).equalsIgnoreCase("6")) {
            if (imageUrl.equalsIgnoreCase(workImageUrl6)) {
                image1_ll.setVisibility(View.VISIBLE);
                image_iv7.setVisibility(View.VISIBLE);
                showGlideImage(workImageUrl6, image_iv6);
            } else if (imageUrl.equalsIgnoreCase(workImageUrl7)) {
                workImageUrl6 = workImageUrl6 + "," + workImageUrl7;
                String imageArray[] = workImageUrl6.split(",");
                int moreImageCount = imageArray.length - 1;
                showMoreImageCount(moreImageCount, image_count6);
                showGlideImage(imageArray[moreImageCount], image_iv6);
                image_iv7.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
                workImageUrl7 = "";
            }
        }
    }

    public void showGlideImage(String imageUrl1, ImageView imageView) {
        String imageUrl;
        if( !cameraImage){
            imageUrl=NetworkUtils.IMAGE_PATH+imageUrl1;

        }
        else{
            imageUrl=imageUrl1;
        }
        Glide.with(RecordAttendanceActivity.this).load(imageUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    private void captureLocation(String locationType) {
        myloc = new LocationDetector(RecordAttendanceActivity.this, locationType);
    }

    private void setCurrentDateAndTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMM, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        SimpleDateFormat amOrPmformat = new SimpleDateFormat("a");
        date_tv.setText(format.format(date));
        time_tv.setText(format.format(date));
        amOrPm_tv.setText(format.format(date));
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

    private void setData() {
        titleTv.setText(getString(R.string.checkin));
        List<AttendanceModel> attedndenceList = new ArrayList<AttendanceModel>();
        attedndenceList=DatabaaseUtils.getAttendanceList(RecordAttendanceActivity.this);
        if(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCHED_IN).equalsIgnoreCase("false") ){

            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IS_PUNCH_IN).equalsIgnoreCase("true")) {
            commentImage_ll.setVisibility(View.VISIBLE);
            punch_tv.setText(getString(R.string.punch_out));

        } else {
            commentImage_ll.setVisibility(View.GONE);
            punch_tv.setText(getString(R.string.punch_in));

        }
                if(attedndenceList.size()>0){

                    if(attedndenceList.get(0).getDate().equalsIgnoreCase(currentDate)) {
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE1, attedndenceList.get(0).getImage1());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE2, attedndenceList.get(0).getImage2());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE3, attedndenceList.get(0).getImage3());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE4, attedndenceList.get(0).getImage4());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE5, attedndenceList.get(0).getImage5());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE6, attedndenceList.get(0).getImage6());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME1, attedndenceList.get(0).getImageTime1());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME2, attedndenceList.get(0).getImageTime2());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME3, attedndenceList.get(0).getImageTime3());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME4, attedndenceList.get(0).getImageTime4());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME5, attedndenceList.get(0).getImageTime5());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME6, attedndenceList.get(0).getImageTime6());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMENT1, attedndenceList.get(0).getComment1());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT2, attedndenceList.get(0).getComment2());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT3, attedndenceList.get(0).getComment3());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT4, attedndenceList.get(0).getComment4());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT5, attedndenceList.get(0).getComment5());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT6, attedndenceList.get(0).getComment6());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS1, attedndenceList.get(0).getGps1());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS2, attedndenceList.get(0).getGps2());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS3, attedndenceList.get(0).getGps3());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS4, attedndenceList.get(0).getGps4());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS5, attedndenceList.get(0).getGps5());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS6, attedndenceList.get(0).getGps6());

                    }

                if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMENT1).equalsIgnoreCase("")) {
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "1");
                    image_iv1.setVisibility(View.VISIBLE);
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "true");

                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT2).equalsIgnoreCase("")) {
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "2");
                    image_iv2.setVisibility(View.VISIBLE);
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT3).equalsIgnoreCase("")) {
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "3");
                    image_iv3.setVisibility(View.VISIBLE);
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT4).equalsIgnoreCase("")) {
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "4");
                    image_iv4.setVisibility(View.VISIBLE);
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT5).equalsIgnoreCase("")) {
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "5");
                    image_iv5.setVisibility(View.VISIBLE);
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT6).equalsIgnoreCase("")) {
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "6");
                    image_iv6.setVisibility(View.VISIBLE);
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                }
                        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMENT1).equalsIgnoreCase("")) {
                            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("")) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE1, "no_image");
                            }
                            isImage1Sync = true;
                        }
                        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT2).equalsIgnoreCase("")) {
                            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("")) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE2, "no_image");
                            }
                            isImage2Sync = true;
                        }

                        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT3).equalsIgnoreCase("")) {
                            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("")) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE3, "no_image");
                            }
                            isImage3Sync = true;
                        }
                        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT4).equalsIgnoreCase("")) {
                            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("")) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE4, "no_image");
                            }
                            isImage4Sync = true;
                        }
                        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT5).equalsIgnoreCase("")) {
                            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("")) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE5, "no_image");
                            }
                            isImage5Sync = true;
                        }
                        if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT6).equalsIgnoreCase("")) {
                            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("")) {
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE6, "no_image");
                            }
                            isImage6Sync = true;
                        }
            }
            showWorkImage();

        }
        else {
            if(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IS_PUNCH_IN).equalsIgnoreCase("false")){
                punch_tv.setText(R.string.punch_in);
                commentImage_ll.setVisibility(View.GONE);
            }else {
                punch_tv.setText(R.string.punch_out);
                if (attedndenceList.size() > 0) {
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.ATTENDANCE_MOBILE_ID, attedndenceList.get(0).getMobileId());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE, attedndenceList.get(0).getDate());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME, attedndenceList.get(0).getInTime());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE1, attedndenceList.get(0).getImage1());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE2, attedndenceList.get(0).getImage2());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE3, attedndenceList.get(0).getImage3());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE4, attedndenceList.get(0).getImage4());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE5, attedndenceList.get(0).getImage5());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE6, attedndenceList.get(0).getImage6());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMENT1, attedndenceList.get(0).getComment1());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT2, attedndenceList.get(0).getComment2());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT3, attedndenceList.get(0).getComment3());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT4, attedndenceList.get(0).getComment4());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT5, attedndenceList.get(0).getComment5());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COMMENT6, attedndenceList.get(0).getComment6());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME1, attedndenceList.get(0).getImageTime1());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME2, attedndenceList.get(0).getImageTime2());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME3, attedndenceList.get(0).getImageTime3());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME4, attedndenceList.get(0).getImageTime4());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME5, attedndenceList.get(0).getImageTime5());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE_TIME6, attedndenceList.get(0).getImageTime1());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS1, attedndenceList.get(0).getGps1());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS2, attedndenceList.get(0).getGps2());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS3, attedndenceList.get(0).getGps3());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS4, attedndenceList.get(0).getGps4());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS5, attedndenceList.get(0).getGps5());
                    sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.GPS6, attedndenceList.get(0).getGps6());

                    workImageUrl1 = attedndenceList.get(0).getImage1();
                    workImageUrl2 = attedndenceList.get(0).getImage2();
                    workImageUrl3 = attedndenceList.get(0).getImage4();
                    workImageUrl4 = attedndenceList.get(0).getImage4();
                    workImageUrl5 = attedndenceList.get(0).getImage5();
                    workImageUrl6 = attedndenceList.get(0).getImage6();


                    if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMENT1).equalsIgnoreCase("")) {
                        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("")) {
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE1, "no_image");
                        }
                        isImage1Sync = true;
                    }
                    if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT2).equalsIgnoreCase("")) {
                        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("")) {
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE2, "no_image");
                        }
                        isImage2Sync = true;
                    }

                    if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT3).equalsIgnoreCase("")) {
                        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("")) {
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE3, "no_image");
                        }
                        isImage3Sync = true;
                    }
                    if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT4).equalsIgnoreCase("")) {
                        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("")) {
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE4, "no_image");
                        }
                        isImage4Sync = true;
                    }
                    if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT5).equalsIgnoreCase("")) {
                        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("")) {
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE5, "no_image");
                        }
                        isImage5Sync = true;
                    }
                    if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("") || !sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT6).equalsIgnoreCase("")) {
                        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("")) {
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IMAGE6, "no_image");
                        }
                        isImage6Sync = true;
                    }


                    // sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC,"true");

                    if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMENT1).equalsIgnoreCase("")) {
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "1");
                        image_iv1.setVisibility(View.VISIBLE);
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "true");

                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT2).equalsIgnoreCase("")) {
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "2");
                        image_iv2.setVisibility(View.VISIBLE);
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT3).equalsIgnoreCase("")) {
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "3");
                        image_iv3.setVisibility(View.VISIBLE);
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT4).equalsIgnoreCase("")) {
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "4");
                        image_iv4.setVisibility(View.VISIBLE);
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT5).equalsIgnoreCase("")) {
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "5");
                        image_iv5.setVisibility(View.VISIBLE);
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                    } else if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("") && sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT6).equalsIgnoreCase("")) {
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.COUNTER, "6");
                        image_iv6.setVisibility(View.VISIBLE);
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC, "false");
                    }
                }
                showWorkImage();
            }
        }


//        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IS_PUNCH_IN).equalsIgnoreCase("true")) {
//            commentImage_ll.setVisibility(View.VISIBLE);
//            punch_tv.setText(getString(R.string.punch_out));
//
//        } else {
//            commentImage_ll.setVisibility(View.GONE);
//            punch_tv.setText(getString(R.string.punch_in));
//
//        }
    }

    private void askPermissions() {
        if ((ActivityCompat.checkSelfPermission(RecordAttendanceActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(RecordAttendanceActivity.this, permissions[1]) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(RecordAttendanceActivity.this, permissions[2]) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(RecordAttendanceActivity.this, permissions[3]) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(RecordAttendanceActivity.this, permissions[4]) != PackageManager.PERMISSION_GRANTED)) {
            //requestPermissions(permissions, ALL_5_PERMISSIONS);
            ActivityCompat.requestPermissions(RecordAttendanceActivity.this, permissions, ALL_5_PERMISSIONS);
        }
        else {
            isPermissionGivenForCamera=true;
        }
    }
    private void askForlocationPermissions(){
        if(ActivityCompat.checkSelfPermission(RecordAttendanceActivity.this,permissions[3])==PackageManager.PERMISSION_GRANTED){
            permissionGivenForLocation=true;
        }
        else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[3])){
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_FROM_SETTING);
            }
        }
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
                        permissionGivenForLocation=true;
                        //Toast.makeText(RecordAttendanceActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case LOCATION_PERMISSION_FROM_SETTING:
                if(grantResults.length>0){
                    if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                        Toast.makeText(RecordAttendanceActivity.this,"To enable the function of this application please enable location permission of the application from the setting screen of the terminal.",Toast.LENGTH_SHORT).show();
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
        } else {
            dismissProgress();
            askPermissions();
            Toast.makeText(RecordAttendanceActivity.this, "Sync failed", Toast.LENGTH_SHORT);
        }
    }

    private void dismissProgress() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    protected class SyncData{


        public SyncData() {

        }

        public String pullAttendance(SyncDataSyncTask syn){
            HttpLoggingInterceptor interceptor=null;
            OkHttpClient.Builder client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS);
            if(BuildConfig.DEBUG){
                interceptor=new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                client.addInterceptor(interceptor);
            }

            final Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(NetworkUtils.SERVER_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            String ispullSuccess="";
            String token = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.TOKEN);
            String lasySyncTime=sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_ATTENDANCE);
            List<AttendanceModel> attendanceModels = new ArrayList<>();
            SyncApiClass syncApiClass= retrofit.create(SyncApiClass.class);
            AttendanceRequest attendanceRequest=new AttendanceRequest();
            attendanceRequest.setToken(token);
            attendanceRequest.setAttendanceList(attendanceModels);
            attendanceRequest.setLastSyncTime(lasySyncTime);

            final Call<AttendanceResponse> responseCall=syncApiClass.syncAttendance(attendanceRequest);
            responseCall.enqueue(new Callback<AttendanceResponse>(){

                @Override
                public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                    int responsecode=response.code();

                    if(responsecode==200){
                        if(response.body().getStatus().equalsIgnoreCase("200")){
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_ATTENDANCE,response.body().getLastSyncTime());
                            AttendanceDataModel attendanceDataModel=null;
                            attendanceDataModel=response.body().getData();
                            if(attendanceDataModel!=null){
                                List<AttendanceModel> attendanceModels = new ArrayList<>();
                                attendanceModels=attendanceDataModel.getAttendanceList();
                                if(attendanceModels != null && attendanceModels.size()>0){
                                    showProgress();
                                    for(int i=0;i<attendanceModels.size();i++){

                                        if (DatabaaseUtils.isAttendanceExist(RecordAttendanceActivity.this, attendanceModels.get(i).getMobileId())) {

                                           DatabaaseUtils.replaceAttendance(RecordAttendanceActivity.this, attendanceModels.get(i));

                                        } else {
                                           DatabaaseUtils.insertAttendance(RecordAttendanceActivity.this, attendanceModels.get(i));
                                        }

                                    }

                                }
                            }
                        }

                        else{

                        }

                    }
                }

                @Override
                public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                    String error="";
                    if(call!=null){
                        error = call.toString();
                    }
                    Log.i("Error in image upload", error);
                }
            });

            return ispullSuccess;

        }

        private void callLoginApi(String userName, String encodedPassword) {
            LoginRequestModel model = new LoginRequestModel();
            model.setUsername(userName);
            model.setPassword(encodedPassword);
            HttpLoggingInterceptor interceptor = null;
            OkHttpClient.Builder client = new OkHttpClient.Builder()
                    .connectTimeout(45, TimeUnit.SECONDS)
                    .readTimeout(45, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG) {
                interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                client.addInterceptor(interceptor);
            }
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkUtils.SERVER_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SyncApiClass apiClass = retrofit.create(SyncApiClass.class);
            Call<LoginResponseModel> responseCall = apiClass.loginApi(model);

            responseCall.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            LoginDataModel dataModel = response.body().getData();
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCHED_IN, dataModel.getPunchedIn().toString());

                        }
                    }
                    dismissProgress();
                }

                @Override
                public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                    String error = "";
                    if (call != null) {
                        error = call.toString();
                    }
                    dismissProgress();

                    Log.i("Error in image upload", error);
                }
            });

        }
    }

    protected class SyncDataSyncTask extends AsyncTask<String, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            SyncData syncData=new SyncData();
            syncData.pullAttendance(this);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            setData();
            super.onPostExecute(aBoolean);
        }


        public void doProgress(int value){
            publishProgress(value);
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

    private void callAttendanceSync(String syncType) {
        SyncManager syncManager = new SyncManager(RecordAttendanceActivity.this);
        syncManager.syncAttendance();
    }

    private void callPullAllData() {
        showProgress();
        PullSyncManager pullSyncManager = new PullSyncManager(getApplicationContext(), RecordAttendanceActivity.this);
        pullSyncManager.pullAllData();

    }

    private void showProgress() {
        dialog = new SpotsDialog(RecordAttendanceActivity.this, getString(R.string.please_wait_msg), R.style.Custom);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showSavingProgress() {
        dialog = new SpotsDialog(RecordAttendanceActivity.this, getString(R.string.saving_your_data), R.style.Custom);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void saveIntoDb(String location, String type, String syncType) {

        if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IS_PUNCH_OUT).equalsIgnoreCase("true")) {
            if (sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IS_FIRST_TIME_PUNCH_IN).equalsIgnoreCase("true")) {
                sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.ATTENDANCE_MOBILE_ID);
                String mobileId = UUID.randomUUID().toString();
                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.ATTENDANCE_MOBILE_ID, mobileId);
                sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IS_FIRST_TIME_PUNCH_IN);
                AttendanceModel model = new AttendanceModel();
                model.setMobileId(mobileId);
                model.setInTime(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME));
                model.setDate(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE));
                model.setCreatedBy(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.USER_ID));
                if (type.equalsIgnoreCase(AppUtils.PUNCH_OUT)) {
                    String outTime = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd hh:mm a");
                    model.setOutTime(outTime);
                    model.setOutGPS(location);
                } else {
                    model.setOutTime("");
                    model.setOutGPS("");
                }
                model.setStatus(AppUtils.IN_PROGRESS);
                model.setInGPS(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.INGPS));
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("no_image")) {
                    model.setImage1(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1));
                } else {
                    model.setImage1("");
                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("no_image")) {
                    model.setImage2(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2));
                } else {
                    model.setImage2("");
                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("no_image")) {
                    model.setImage3(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3));
                } else {
                    model.setImage3("");
                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("no_image")) {
                    model.setImage4(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4));
                } else {
                    model.setImage4("");
                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("no_image")) {
                    model.setImage5(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5));
                } else {
                    model.setImage5("");

                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("no_image")) {
                    model.setImage6(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6));
                } else {
                    model.setImage6("");
                }
                model.setComment1(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMENT1));
                model.setComment2(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT2));
                model.setComment3(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT3));
                model.setComment4(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT4));
                model.setComment5(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT5));
                model.setComment6(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT6));
                model.setGps1(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS1));
                model.setGps2(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS2));
                model.setGps3(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS3));
                model.setGps4(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS4));
                model.setGps5(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS5));
                model.setGps6(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS6));
                model.setCreatedDate(AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd HH:mm:ss"));
                model.setImageTime1(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME1));
                model.setImageTime2(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME2));
                model.setImageTime3(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME3));
                model.setImageTime4(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME4));
                model.setImageTime5(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME5));
                model.setImageTime6(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME6));
                model.setName(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.NAME));
                DatabaaseUtils.insertAttendance(RecordAttendanceActivity.this, model);
                saveImagesInDB(model);


            } else {
                String monileId = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.ATTENDANCE_MOBILE_ID);
                AttendanceModel model = DatabaaseUtils.getAttendanceDetailsByMobileId(RecordAttendanceActivity.this, monileId);
                model.setInTime(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME));
                model.setDate(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE));
                if (type.equalsIgnoreCase(AppUtils.PUNCH_OUT)) {
                    String outTime = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd hh:mm a");
                    model.setOutTime(outTime);
                    model.setOutGPS(location);
                } else {
                    model.setOutTime("");
                    model.setOutGPS("");
                }
                model.setId(model.getId());
                model.setCreatedDate(AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd HH:mm:ss"));
                model.setStatus(AppUtils.IN_PROGRESS);
                model.setCreatedBy(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.USER_ID));
                //model.setInGPS(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.INGPS));
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1).equalsIgnoreCase("no_image") ) {
                    model.setImage1(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE1));
                } else {
                    model.setImage1("");
                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2).equalsIgnoreCase("no_image")) {
                    model.setImage2(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE2));
                } else {
                    model.setImage2("");
                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3).equalsIgnoreCase("no_image")) {
                    model.setImage3(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE3));
                } else {
                    model.setImage3("");
                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4).equalsIgnoreCase("no_image")) {
                    model.setImage4(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE4));
                } else {
                    model.setImage4("");
                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5).equalsIgnoreCase("no_image")) {
                    model.setImage5(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE5));
                } else {
                    model.setImage5("");

                }
                if (!sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6).equalsIgnoreCase("no_image")) {
                    model.setImage6(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE6));
                } else {
                    model.setImage6("");
                }
                model.setComment1(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMENT1));
                model.setComment2(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT2));
                model.setComment3(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT3));
                model.setComment4(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT4));
                model.setComment5(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT5));
                model.setComment6(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.COMMENT6));
                model.setGps1(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS1));
                model.setGps2(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS2));
                model.setGps3(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS3));
                model.setGps4(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS4));
                model.setGps5(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS5));
                model.setGps6(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.GPS6));
                model.setImageTime1(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME1));
                model.setImageTime2(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME2));
                model.setImageTime3(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME3));
                model.setImageTime4(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME4));
                model.setImageTime5(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME5));
                model.setImageTime6(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.IMAGE_TIME6));
                model.setName(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.NAME));
                String currentDate = AppUtils.currentDateTime(RecordAttendanceActivity.this, "yyyy-MM-dd");
                model.setDate(currentDate);
                DatabaaseUtils.replaceAttendance(RecordAttendanceActivity.this, model);
                saveImagesInDB(model);


            }
            sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IS_PUNCH_OUT);
            if (AppUtils.isOnline(RecordAttendanceActivity.this)) {
                callAttendanceSync(syncType);
            } else {
                showDataSyncRequiredSnackBar();
            }
            if (type.equalsIgnoreCase(AppUtils.PUNCH_OUT)) {
                punch_tv.setText(getString(R.string.punch_in));
                commentImage_ll.setVisibility(View.GONE);
                AppUtils.showAllert(RecordAttendanceActivity.this, R.string.punch_out_success_msg);
                sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.IS_PUNCH_IN);
                sharedPrefHelper.removeFromSharedPrefs(SharedPrefHelper.ATTENDANCE_MOBILE_ID);
            }
            else  if (type.equalsIgnoreCase(AppUtils.SAVE)) {
                Toast.makeText(RecordAttendanceActivity.this, "Successfully saved your data", Toast.LENGTH_SHORT).show();
            }
            else if(type.equalsIgnoreCase(AppUtils.PUNCH_IN)){
                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_PUNCH_IN, "true");
                commentImage_ll.setVisibility(View.VISIBLE);
                punch_tv.setText(getString(R.string.punch_out));
                comment_et.setFocusable(true);
                AppUtils.showAllert(RecordAttendanceActivity.this, R.string.punch_in_success_msg);
            }
            dismissProgress();
            workImageUrl1 = "";
            workImageUrl2 = "";
            workImageUrl3 = "";
            workImageUrl4 = "";
            workImageUrl5 = "";
            workImageUrl6 = "";
            comment_et.setText("");

        }

    }
    private void saveImagesInDB(AttendanceModel model){
        for (int i = 0; i <= 5; i++) {
            File imageFile = null;
            if (i == 0 ) {

                if(!isImage1Sync) {

                    if (model.getImage1() != null) {
                        if (!model.getImage1().equalsIgnoreCase("")) {
                            String[] imageArray = model.getImage1().split(",");
                            for (int a = 0; a < imageArray.length; a++) {
                                if (!DataBaseManager.getInstance().isImagePresentInDataBase(imageArray[a])) {
                                    ImageSync imageSync = new ImageSync();
                                    imageSync.setId(UUID.randomUUID().toString());
                                    imageSync.setImageUrl(imageArray[a]);
                                    imageFile = new File(imageArray[a]);
                                    if (imageFile.exists()) {
                                        long imageSize = imageFile.length();
                                        imageSync.setImageParam(imageSize);
                                    }

                                    imageSync.setStatus(AppUtils.IMAGE_NOT_SYNCED);
                                    DataBaseManager.getInstance().insertImage(imageSync);


                                }
                            }

                        }
                    }
                }
            } else if (i == 1) {
                if(!isImage2Sync) {
                    if (model.getImage2() != null) {
                        if (!model.getImage2().equalsIgnoreCase("")) {
                            String[] imageArray = model.getImage2().split(",");
                            for (int b = 0; b < imageArray.length; b++) {
                                if (!DataBaseManager.getInstance().isImagePresentInDataBase(imageArray[b])) {
                                    ImageSync imageSync = new ImageSync();
                                    imageSync.setId(UUID.randomUUID().toString());
                                    imageSync.setImageUrl(imageArray[b]);
                                    imageFile = new File(imageArray[b]);
                                    if (imageFile.exists()) {
                                        long imageSize = imageFile.length();
                                        imageSync.setImageParam(imageSize);
                                    }
                                    imageSync.setStatus(AppUtils.IMAGE_NOT_SYNCED);
                                    DataBaseManager.getInstance().insertImage(imageSync);

                                }
                            }
                        }
                    }
                }

            } else if (i == 2) {
                if(!isImage3Sync) {
                    if (model.getImage3() != null) {
                        if (!model.getImage3().equalsIgnoreCase("")) {
                            String[] imageArray = model.getImage3().split(",");
                            for (int c = 0; c < imageArray.length; c++) {
                                if (!DataBaseManager.getInstance().isImagePresentInDataBase(imageArray[c])) {
                                    ImageSync imageSync = new ImageSync();
                                    imageSync.setId(UUID.randomUUID().toString());
                                    imageSync.setImageUrl(imageArray[c]);
                                    imageFile = new File(imageArray[c]);
                                    if (imageFile.exists()) {
                                        long imageSize = imageFile.length();
                                        imageSync.setImageParam(imageSize);
                                    }
                                    imageSync.setStatus(AppUtils.IMAGE_NOT_SYNCED);
                                    DataBaseManager.getInstance().insertImage(imageSync);
                                }
                            }
                        }
                    }
                }

            } else if (i == 3) {
                if(!isImage4Sync){
                if (model.getImage4() != null) {
                    if (!model.getImage4().equalsIgnoreCase("")) {
                        String[] imageArray = model.getImage4().split(",");
                        List<ImageFIleModel> fIleModels = new ArrayList<>();
                        for (int d = 0; d < imageArray.length; d++) {
                            if (!DataBaseManager.getInstance().isImagePresentInDataBase(imageArray[d])) {
                                ImageSync imageSync = new ImageSync();
                                imageSync.setId(UUID.randomUUID().toString());
                                imageSync.setImageUrl(imageArray[d]);
                                imageFile = new File(imageArray[d]);
                                if (imageFile.exists()) {
                                    long imageSize = imageFile.length();
                                    imageSync.setImageParam(imageSize);
                                }
                                imageSync.setStatus(AppUtils.IMAGE_NOT_SYNCED);
                                DataBaseManager.getInstance().insertImage(imageSync);
                            }
                        }
                    }
                }
                }
            } else if (i == 4) {
                if(!isImage5Sync) {
                    if (model.getImage5() != null) {
                        if (!model.getImage5().equalsIgnoreCase("")) {
                            String[] imageArray = model.getImage5().split(",");
                            List<ImageFIleModel> fIleModels = new ArrayList<>();
                            for (int e = 0; e < imageArray.length; e++) {
                                if (!DataBaseManager.getInstance().isImagePresentInDataBase(imageArray[e])) {
                                    ImageSync imageSync = new ImageSync();
                                    imageSync.setId(UUID.randomUUID().toString());
                                    imageSync.setImageUrl(imageArray[e]);
                                    imageFile = new File(imageArray[e]);
                                    if (imageFile.exists()) {
                                        long imageSize = imageFile.length();
                                        imageSync.setImageParam(imageSize);
                                    }
                                    imageSync.setStatus(AppUtils.IMAGE_NOT_SYNCED);
                                    DataBaseManager.getInstance().insertImage(imageSync);

                                }
                            }
                        }
                    }
                }
            } else if (i == 5) {
                if (!isImage6Sync) {
                    if (model.getImage6() != null) {
                        if (!model.getImage6().equalsIgnoreCase("")) {
                            String[] imageArray = model.getImage6().split(",");
                            List<ImageFIleModel> fIleModels = new ArrayList<>();
                            for (int f = 0; f < imageArray.length; f++) {
                                if (!DataBaseManager.getInstance().isImagePresentInDataBase(imageArray[f])) {
                                    ImageSync imageSync = new ImageSync();
                                    imageSync.setId(UUID.randomUUID().toString());
                                    imageSync.setImageUrl(imageArray[f]);
                                    imageFile = new File(imageArray[f]);
                                    if (imageFile.exists()) {
                                        long imageSize = imageFile.length();
                                        imageSync.setImageParam(imageSize);
                                    }
                                    imageSync.setStatus(AppUtils.IMAGE_NOT_SYNCED);
                                    DataBaseManager.getInstance().insertImage(imageSync);
                                }
                            }
                        }
                    }
                }
            }

        }
    }


}