package com.trisysit.epc_task_android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.trisysit.epc_task_android.EPCTaskUpdateAppication;
import com.trisysit.epc_task_android.R;
import com.trisysit.epc_task_android.utils.SharedPrefHelper;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPrefHelper = SharedPrefHelper.getInstance(EPCTaskUpdateAppication.getContext());
        String token = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.TOKEN);
        if (token != null && (!token.equalsIgnoreCase(""))) {
                Intent intent = new Intent(SplashScreenActivity.this, TaskUpdateActivity.class);
                startActivity(intent);
                finish();
        } else {
            showScreen();
        }
    }
    private void showScreen() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        };
        Timer t = new Timer();
        t.schedule(task, 5000);
    }
}
