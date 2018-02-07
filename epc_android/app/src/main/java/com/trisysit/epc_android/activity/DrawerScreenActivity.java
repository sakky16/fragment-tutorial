package com.trisysit.epc_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.network.SyncApiClass;
import com.trisysit.epc_android.serverModel.LogoutRequestModel;
import com.trisysit.epc_android.serverModel.LogoutResponseModel;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;
import com.trisysit.epc_android.utils.NetworkUtils;
import com.trisysit.epc_android.utils.SharedPrefHelper;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DrawerScreenActivity extends AppCompatActivity {
    public Drawer.Result result;
    public DrawerLayout drawerLayout;
    private AlertDialog progressDialog;
    public ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_screen);

    }

    public void setUpNavigationDrawer(final Activity activity, final View rootView, final Toolbar toolbar) {
        toolbar.setBackgroundColor(getResources().getColor(R.color.darkBlue));
        toolbar.setNavigationIcon(R.drawable.ham_new_icon);
        result = new Drawer().withActivity(activity)
                .withHeader(R.layout.header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.attendence)).withName("Record Attendance").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(0).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue)),
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.attendance_list)).withName("Attendance").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(1).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue)),
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.project_icon)).withName("Project List").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(2).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue)),
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.change_password)).withName("Change Password").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(3).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue)),
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.sync_icon)).withName("Sync Information").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(5).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue)),
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.new_logout)).withName("Logout").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(4).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue))
                        //new PrimaryDrawerItem().withName("Version 12.2.0.350").setEnabled(false).withTextColor(activity.getResources().getColor(R.color.Gray)).withIdentifier(5).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue))
                )
                .withSliderBackgroundColor(getResources().getColor(R.color.White))
                .withFullscreen(true)
                .withHeaderClickable(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            switch (drawerItem.getIdentifier()) {
                                case 0: {
                                    Intent i = new Intent(DrawerScreenActivity.this, RecordAttendanceActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 1: {
                                    Intent i = new Intent(DrawerScreenActivity.this, AttendenceActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 2: {
                                    Intent i = new Intent(DrawerScreenActivity.this, ProjectListingActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 3: {
                                    Intent i = new Intent(DrawerScreenActivity.this, ChangePasswordActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 5:{
                                    Intent i = new Intent(DrawerScreenActivity.this, SyncInfoActivity.class);
                                    startActivity(i);
                                    finish();
                                    break;
                                }
                                case 4: {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(DrawerScreenActivity.this);
                                    builder.setMessage("Are you sure You want to logout");
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(AppUtils.isOnline(DrawerScreenActivity.this)){
                                                if(AppUtils.getSyncDetails(DrawerScreenActivity.this)){
                                                    Toast.makeText(DrawerScreenActivity.this,"Please sync all the data first before Log out",Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(DrawerScreenActivity.this,SyncInfoActivity.class);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    callLoginActivityAfterLogout();
                                                }
                                            }
                                            else {
                                                Toast.makeText(DrawerScreenActivity.this,getString(R.string.no_internet_msg),Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    });
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog dialog=builder.create();
                                    dialog.show();
                                    break;
                                }

                            }
                        }
                    }
                })
                .build();
        drawerLayout = result.getDrawerLayout();
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                rootView.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(mToggle);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.parent);
        TextView name=(TextView)linearLayout.findViewById(R.id.name_tv);
        name.setText(SharedPrefHelper.getInstance(DrawerScreenActivity.this).getFromSharedPrefs(SharedPrefHelper.NAME));

    }
    public void setUpNavigationDrawerForAttendAdmin(final Activity activity, final View rootView, final Toolbar toolbar){
        toolbar.setBackgroundColor(getResources().getColor(R.color.darkBlue));
        toolbar.setNavigationIcon(R.drawable.ham_new_icon);
        result = new Drawer().withActivity(activity)
                .withHeader(R.layout.header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.attendence)).withName("Pin Attendance").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(0).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue)),
                        //new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.sync_icon)).withName("Sync Information").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(5).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue)),
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.new_logout)).withName("Logout").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(4).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue))
                        //new PrimaryDrawerItem().withName("Version 12.2.0.350").setEnabled(false).withTextColor(activity.getResources().getColor(R.color.Gray)).withIdentifier(5).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue))
                )
                .withSliderBackgroundColor(getResources().getColor(R.color.White))
                .withFullscreen(true)
                .withHeaderClickable(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            switch (drawerItem.getIdentifier()) {
                                case 0: {
                                    Intent i = new Intent(DrawerScreenActivity.this, AttendaneAdminRecordAttendanceActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 1: {
                                    Intent i = new Intent(DrawerScreenActivity.this, AttendenceActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 2: {
                                    Intent i = new Intent(DrawerScreenActivity.this, ProjectListingActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 3: {
                                    Intent i = new Intent(DrawerScreenActivity.this, ChangePasswordActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 5:{
                                    Intent i = new Intent(DrawerScreenActivity.this, SyncInfoActivity.class);
                                    startActivity(i);
                                    finish();
                                    break;
                                }
                                case 4: {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(DrawerScreenActivity.this);
                                    builder.setMessage("Are you sure You want to logout");
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(AppUtils.isOnline(DrawerScreenActivity.this)){
                                                if(SharedPrefHelper.getInstance(DrawerScreenActivity.this).getFromSharedPrefs(SharedPrefHelper.ROLE_NAME).equalsIgnoreCase(AppUtils.ROLE_ATTENDANCE_ADMIN)){
                                                    callLoginActivityAfterLogout();
                                                }
                                                else {
                                                    if (AppUtils.getSyncDetails(DrawerScreenActivity.this)) {
                                                        Toast.makeText(DrawerScreenActivity.this, "Please sync all the data first before Log out", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(DrawerScreenActivity.this, SyncInfoActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        callLoginActivityAfterLogout();
                                                    }
                                                }
                                            }
                                            else {
                                                Toast.makeText(DrawerScreenActivity.this,getString(R.string.no_internet_msg),Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    });
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog dialog=builder.create();
                                    dialog.show();
                                    break;
                                }

                            }
                        }
                    }
                })
                .build();
        drawerLayout = result.getDrawerLayout();
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                rootView.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(mToggle);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.parent);
        TextView name=(TextView)linearLayout.findViewById(R.id.name_tv);
        name.setText(SharedPrefHelper.getInstance(DrawerScreenActivity.this).getFromSharedPrefs(SharedPrefHelper.NAME));

    }

    private void callLogoutAPi(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS).build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String token=SharedPrefHelper.getInstance(DrawerScreenActivity.this).getFromSharedPrefs(SharedPrefHelper.TOKEN);
        LogoutRequestModel requestModel=new LogoutRequestModel();
        requestModel.setToken(token);
        SyncApiClass apiClass=retrofit.create(SyncApiClass.class);
        showProgress();
        Call<LogoutResponseModel> requestCall=apiClass.logoutAPI(requestModel);
        requestCall.enqueue(new Callback<LogoutResponseModel>() {
            @Override
            public void onResponse(Call<LogoutResponseModel> call, Response<LogoutResponseModel> response) {
                if(response.code()==200){
                    if(response.body().getStatus().equalsIgnoreCase("200")){
                       callLoginActivityAfterLogout();
                    }
                    Toast.makeText(DrawerScreenActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DrawerScreenActivity.this, getString(R.string.server_connection_error_msg), Toast.LENGTH_SHORT).show();
                }
                dismissProgress();
            }

            @Override
            public void onFailure(Call<LogoutResponseModel> call, Throwable t) {
                String error = call.toString();
                dismissProgress();
                Log.i("Error in image upload", error);
            }
        });
    }
    private void callLoginActivityAfterLogout(){
        removeSharedPrefDuringLogout();
        DatabaaseUtils.deleteAllTableData(DrawerScreenActivity.this);
        DataBaseManager.getInstance().clearTable();
        Intent i = new Intent(DrawerScreenActivity.this, UserLoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
    private void showProgress(){
        progressDialog=new SpotsDialog(DrawerScreenActivity.this,getString(R.string.please_wait_msg),R.style.Custom);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void dismissProgress(){
        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }
    private void removeSharedPrefDuringLogout(){
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.TOKEN);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.NAME);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.USERNAME);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.PASSWORD);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_TASK);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_ATTENDANCE);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.IMAGE1);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.IMAGE2);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.IMAGE3);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.IMAGE4);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.IMAGE5);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.IMAGE6);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.COMENT1);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.COMMENT2);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.COMMENT3);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.COMMENT4);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.COMMENT5);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.COMMENT6);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.PUNCHED_IN);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.IS_PUNCH_IN);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.IS_IMAGE_SYNC);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.IS_USER_MULTIPLE_ATT);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.PUNCH_IN_DATE);
        SharedPrefHelper.getInstance(DrawerScreenActivity.this).removeFromSharedPrefs(SharedPrefHelper.PUNCH_IN_TIME);
    }
}