package com.trisysit.epc_task_android.utils;

import com.trisysit.epc_task_android.serverModel.LoginRequestModel;
import com.trisysit.epc_task_android.serverModel.LoginResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by trisys on 6/2/18.
 */

public interface SyncApiClass {
    @POST(NetworkUtils.LOGIN)
    Call<LoginResponseModel> loginApi(@Body LoginRequestModel requestModel);
}
