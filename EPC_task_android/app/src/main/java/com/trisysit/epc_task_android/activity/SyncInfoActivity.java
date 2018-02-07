package com.trisysit.epc_task_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.trisysit.epc_task_android.R;

public class SyncInfoActivity extends DrawerActivity {
    private View rootView;
    private Toolbar toolbar;
    private TextView titleTv,task_sync_info_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_info);
        getWidgets();
        setUpNavigationDrawer(SyncInfoActivity.this,rootView,toolbar);
    }
    private void getWidgets(){
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText("Sync Information");
    }
}
