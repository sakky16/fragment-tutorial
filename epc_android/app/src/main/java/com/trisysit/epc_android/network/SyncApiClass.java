package com.trisysit.epc_android.network;

import com.trisysit.epc_android.serverModel.AttendanceRequest;
import com.trisysit.epc_android.serverModel.AttendanceResponse;
import com.trisysit.epc_android.serverModel.ChangePwrdRequestModel;
import com.trisysit.epc_android.serverModel.ChangePwrdResponseModel;
import com.trisysit.epc_android.serverModel.GetUserDetailsRequestModel;
import com.trisysit.epc_android.serverModel.GetUserDetailsResponseModel;
import com.trisysit.epc_android.serverModel.LoginRequestModel;
import com.trisysit.epc_android.serverModel.LoginResponseModel;
import com.trisysit.epc_android.serverModel.LogoutRequestModel;
import com.trisysit.epc_android.serverModel.LogoutResponseModel;
import com.trisysit.epc_android.serverModel.TaskRequestModel;
import com.trisysit.epc_android.serverModel.TaskResponseModel;
import com.trisysit.epc_android.utils.NetworkUtils;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by trisys on 9/12/17.
 */

public interface SyncApiClass {
    @POST(NetworkUtils.SYNC_ATTENDANCE)
    Call<AttendanceResponse> syncAttendance(@Body AttendanceRequest request);

    @POST(NetworkUtils.SYNC_TASK)
    Call<TaskResponseModel> syncTask(@Body TaskRequestModel requestModel);

    @POST(NetworkUtils.LOGIN)
    Call<LoginResponseModel> loginApi(@Body LoginRequestModel requestModel);

    @POST(NetworkUtils.LOGOUT)
    Call<LogoutResponseModel> logoutAPI(@Body LogoutRequestModel requestModel);

    @Multipart
    @POST(NetworkUtils.IMAGE_SYNC)
    Call<Object> callSyncImage(@Header("Id")String id, @Header("imageUrl") String imageName,@Header("imageParam")String imageParam,
                               @Header("token") String token,
                               @Part MultipartBody.Part file);

    @POST(NetworkUtils.CHANGE_PASSWORD)
    Call<ChangePwrdResponseModel> change_pwrd_API(@Body ChangePwrdRequestModel requestModel);

    @POST(NetworkUtils.GET_USER_DETAILS)
    Call<GetUserDetailsResponseModel> get_user_API(@Body GetUserDetailsRequestModel requestModel);

}
