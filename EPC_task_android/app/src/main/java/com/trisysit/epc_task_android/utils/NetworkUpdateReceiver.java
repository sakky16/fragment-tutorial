package com.trisysit.epc_task_android.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by trisys on 7/2/18.
 */

public class NetworkUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        NetworkInfo mobileNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(activeNetworkInfo!=null){

        }
        else if(mobileNetInfo!=null){

        }
        else {
            AfterNetworkSyncManager manager=new AfterNetworkSyncManager(context);
            manager.syncAllData();
        }

    }
}
