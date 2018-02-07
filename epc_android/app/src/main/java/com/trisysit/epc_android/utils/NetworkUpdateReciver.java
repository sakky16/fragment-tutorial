package com.trisysit.epc_android.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by trisys on 9/12/17.
 */

public class NetworkUpdateReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo
                (ConnectivityManager.TYPE_MOBILE );
        if ( activeNetInfo != null )
        {
            /*SyncManager offlineSyncManager=new SyncManager(context);
            offlineSyncManager.syncAllData();*/
            AfterNetworkSyncManager manager=new AfterNetworkSyncManager(context);
            manager.syncAllData();


        }
        else if (mobNetInfo != null )
        {

        }
        else
        {
            //No Internet connection... Waiting for Network
        }
    }
}
