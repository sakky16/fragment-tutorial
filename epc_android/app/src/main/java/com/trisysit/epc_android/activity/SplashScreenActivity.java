package com.trisysit.epc_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.EPCApplication;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.SharedPrefHelper;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPrefHelper = SharedPrefHelper.getInstance(EPCApplication.getContext());
        String token = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.TOKEN);
        if (token != null && (!token.equalsIgnoreCase(""))) {
            if(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.ROLE_NAME).equalsIgnoreCase(AppUtils.ROLE_ATTENDANCE_ADMIN)){
                Intent intent = new Intent(SplashScreenActivity.this, AttendaneAdminRecordAttendanceActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(SplashScreenActivity.this, RecordAttendanceActivity.class);
                startActivity(intent);
                finish();
            }

        } else {
            showScreen();
        }
    }

    private void showScreen() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish();

            }
        };
        Timer t = new Timer();
        t.schedule(task, 5000);
    }
}
