package com.trisysit.epc_android.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.utils.AfterNetworkSyncManager;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;
import com.trisysit.epc_android.utils.SharedPrefHelper;
import com.trisysit.epc_android.utils.SyncManager;

public class SyncInfoActivity extends DrawerScreenActivity {
    private View rootView;
    private Toolbar toolbar;
    private TextView titleTv,atten_sync_info_tv,image_sync_info_tv,task_sync_info_tv;
    private Button sync_all_data_btn;
    private  int pendingAttendanceSync,pendingImageSyncData,pendingTaskSyncData;
    public static final String SYNC_ACTION = "com.trisysit.epc_android.activity.SYNC_ACTION";
    private final IntentFilter intentFilter = new IntentFilter(SYNC_ACTION);
    private BroadcastReceiver syncBroadcastReciver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(syncBroadcastReciver);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_info);
        getWidgets();
        if(SharedPrefHelper.getInstance(SyncInfoActivity.this).getFromSharedPrefs(SharedPrefHelper.ROLE_NAME).equalsIgnoreCase(AppUtils.ROLE_ATTENDANCE_ADMIN)){
            setUpNavigationDrawerForAttendAdmin(SyncInfoActivity.this, rootView, toolbar);
        }
        else {
            setUpNavigationDrawer(SyncInfoActivity.this, rootView, toolbar);
        }
        getSyncData();
        setListener();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(SharedPrefHelper.getInstance(SyncInfoActivity.this).getFromSharedPrefs(SharedPrefHelper.ROLE_NAME).equalsIgnoreCase(AppUtils.ROLE_ATTENDANCE_ADMIN)){
            Intent intent=new Intent(SyncInfoActivity.this,AttendaneAdminRecordAttendanceActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent=new Intent(SyncInfoActivity.this,RecordAttendanceActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppUtils.getSyncDetails(getApplicationContext())){
            findViewById(R.id.snack_bar).setVisibility(View.VISIBLE);
        }
        else {
            findViewById(R.id.snack_bar).setVisibility(View.GONE);
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(syncBroadcastReciver, intentFilter);
    }
    private void getWidgets(){
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        atten_sync_info_tv=(TextView)findViewById(R.id.atten_sync_info_tv);
        sync_all_data_btn=(Button)findViewById(R.id.sync_all_data_btn);
        titleTv.setText("Sync Information");
        image_sync_info_tv=(TextView)findViewById(R.id.image_sync_info_tv);
        task_sync_info_tv=(TextView)findViewById(R.id.task_sync_info_tv);
    }
    private void getSyncData(){
        pendingAttendanceSync= DatabaaseUtils.getAttendListForSync(SyncInfoActivity.this, AppUtils.IN_PROGRESS).size();
        pendingImageSyncData= DataBaseManager.getInstance().getImageListByStatus().size();
        pendingTaskSyncData=DataBaseManager.getInstance().getTaskListForSync().size();
        atten_sync_info_tv.setText("Number of Attendance pending to sync : "+pendingAttendanceSync);
        image_sync_info_tv.setText("Number of image pending to sync : "+pendingImageSyncData);
        task_sync_info_tv.setText("Number of daily update pending to sync : "+pendingTaskSyncData);
    }
    private void setListener(){
        sync_all_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppUtils.isOnline(SyncInfoActivity.this)){
                    pendingAttendanceSync= DatabaaseUtils.getAttendListForSync(SyncInfoActivity.this, AppUtils.IN_PROGRESS).size();
                    pendingImageSyncData= DataBaseManager.getInstance().getImageListByStatus().size();
                    pendingTaskSyncData=DataBaseManager.getInstance().getTaskListForSync().size();
                    atten_sync_info_tv.setText("Number of Attendance pending to sync : "+pendingAttendanceSync);
                    image_sync_info_tv.setText("Number of image pending to sync : "+pendingImageSyncData);
                    task_sync_info_tv.setText("Number of daily update pending to sync : "+pendingTaskSyncData);
                    int syncCheckValue=pendingAttendanceSync+pendingImageSyncData+pendingTaskSyncData;
                    if(syncCheckValue>0) {
                        Toast.makeText(SyncInfoActivity.this,"Syncing in progress...please wait",Toast.LENGTH_SHORT).show();
                        AfterNetworkSyncManager syncManager = new AfterNetworkSyncManager(SyncInfoActivity.this);
                        syncManager.syncAllData();

                    }
                    else {
                        if(findViewById(R.id.snack_bar).getVisibility()==View.VISIBLE){
                            findViewById(R.id.snack_bar).setVisibility(View.GONE);
                        }
                        Toast.makeText(SyncInfoActivity.this,"Data sync not required",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SyncInfoActivity.this,R.string.no_internet_msg,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
