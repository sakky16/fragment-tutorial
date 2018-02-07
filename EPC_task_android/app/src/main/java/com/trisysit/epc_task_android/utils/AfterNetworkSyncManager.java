package com.trisysit.epc_task_android.utils;

import android.content.Context;

/**
 * Created by trisys on 7/2/18.
 */

public class AfterNetworkSyncManager {
    Context context;
    SharedPrefHelper sharedPrefHelper;

    public AfterNetworkSyncManager(Context context){
        this.context=context;
        sharedPrefHelper=SharedPrefHelper.getInstance(context);
    }
    public void syncAllData(){

    }
}
