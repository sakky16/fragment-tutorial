package com.trisysit.epc_android.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trisysit.epc_android.BuildConfig;
import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.SqliteDatabase.ImageSync;
import com.trisysit.epc_android.SqliteDatabase.ProjectTable;
import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.model.AttendanceModel;
import com.trisysit.epc_android.model.ImageFIleModel;
import com.trisysit.epc_android.model.ImageSyncModel;
import com.trisysit.epc_android.network.SyncApiClass;
import com.trisysit.epc_android.serverModel.AttendanceDataModel;
import com.trisysit.epc_android.serverModel.AttendanceRequest;
import com.trisysit.epc_android.serverModel.AttendanceResponse;
import com.trisysit.epc_android.serverModel.TaskDataModel;
import com.trisysit.epc_android.serverModel.TaskRequestModel;
import com.trisysit.epc_android.serverModel.TaskResponseModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by trisys on 28/12/17.
 */

public class AfterNetworkSyncManager {
    Context context;
    SharedPrefHelper sharedPrefHelper;

    public AfterNetworkSyncManager(Context context) {
        this.context = context;
        sharedPrefHelper = SharedPrefHelper.getInstance(context);
    }

    public void syncAllData() {
        syncAttendance();
        syncTaskUpdate();
        syncImages();

    }

    public void syncTaskUpdate() {
        List<ProjectTask> taskList = new ArrayList<>();
        String token = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.TOKEN);
        String lastSyncTime = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_TASK);
        HttpLoggingInterceptor interceptor = null;
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.SERVER_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        taskList = DataBaseManager.getInstance().getTaskListForSync();
        if (taskList.size() > 0) {
            for (ProjectTask projectTask : taskList) {
                projectTask.setTaskStatus("OPEN");
            }
            SyncApiClass syncApiClass = retrofit.create(SyncApiClass.class);
            TaskRequestModel requestModel = new TaskRequestModel();
            requestModel.setToken(token);
            requestModel.setTasks(taskList);
            requestModel.setLastSyncTime(lastSyncTime);
            Call<TaskResponseModel> responseCall = syncApiClass.syncTask(requestModel);
            responseCall.enqueue(new Callback<TaskResponseModel>() {
                @Override
                public void onResponse(Call<TaskResponseModel> call, Response<TaskResponseModel> response) {
                    int responseCode = response.code();
                    if (responseCode == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_TASK, response.body().getLastSyncTime());
                            TaskDataModel dataModel = null;
                            dataModel = response.body().getData();
                            if (dataModel != null) {
                                List<ProjectTask> taskList = dataModel.getTasks();
                                List<ProjectTable> projectList = dataModel.getProjects();
                                if (taskList != null && taskList.size() > 0) {
                                    DataBaseManager.getInstance().insertTaskList(taskList);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TaskResponseModel> call, Throwable t) {
                    String error = "";
                    if (call != null) {
                        error = call.toString();
                    }

                    Log.i("Error in image upload", error);
                }
            });

        }
    }

    public void syncAttendance() {
        List<AttendanceModel> attendanceModels = new ArrayList<>();
        final List<ImageSyncModel> imageSyncModelList = new ArrayList<>();
        String token = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.TOKEN);
        String name = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.NAME);
        String lastSyncTime = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_ATTENDANCE);
        HttpLoggingInterceptor interceptor = null;
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.SERVER_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        attendanceModels = DatabaaseUtils.getAttendListForSync(context, AppUtils.IN_PROGRESS);
        if (attendanceModels.size() > 0) {
            for (AttendanceModel attendanceModel : attendanceModels) {
                attendanceModel.setName(name);
                attendanceModel.setStatus(AppUtils.ACTIVE);
            }
            SyncApiClass syncApiClass = retrofit.create(SyncApiClass.class);
            AttendanceRequest attendanceRequest = new AttendanceRequest();
            attendanceRequest.setToken(token);
            attendanceRequest.setAttendanceList(attendanceModels);
            attendanceRequest.setLastSyncTime(lastSyncTime);
            Call<AttendanceResponse> responseCall = syncApiClass.syncAttendance(attendanceRequest);
            responseCall.enqueue(new Callback<AttendanceResponse>() {
                @Override
                public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                    if (response.code() == 200) {
                        List<AttendanceModel> attendanceModels = new ArrayList<>();
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            if (response.body().getData() != null) {
                                AttendanceDataModel dataModel = null;
                                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_ATTENDANCE, response.body().getLastSyncTime());
                                dataModel = response.body().getData();
                                attendanceModels = dataModel.getAttendanceList();
                                if (attendanceModels.size() > 0) {
                                    for (int i = 0; i < attendanceModels.size(); i++) {
                                        if (DatabaaseUtils.isAttendanceExist(context, attendanceModels.get(i).getMobileId())) {
                                            DatabaaseUtils.replaceAttendance(context, attendanceModels.get(i));
                                        } else {
                                            DatabaaseUtils.insertAttendance(context, attendanceModels.get(i));
                                        }
                                    }
                                    AttendanceModel model = DatabaaseUtils.getAttendanceDetailsByMobileId(context, sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.ATTENDANCE_MOBILE_ID));
                                    if (model != null) {
                                        List<ImageSync> imageSyncList = DataBaseManager.getInstance().getImageListByStatus();
                                        if (imageSyncList.size() > 0) {
                                            List<ImageFIleModel> fIleModels = new ArrayList<>();
                                            ImageSyncModel model1 = new ImageSyncModel();
                                            for (int i = 0; i < imageSyncList.size(); i++) {
                                                ImageFIleModel imageFIleModel = new ImageFIleModel();
                                                imageFIleModel.setImageName(imageSyncList.get(i).getImageUrl());
                                                imageFIleModel.setFile(new File(imageSyncList.get(i).getImageUrl()));
                                                imageFIleModel.setImageParam(imageSyncList.get(i).getImageParam());
                                                fIleModels.add(imageFIleModel);
                                            }
                                            model1.setImageFIleModels(fIleModels);
                                            imageSyncModelList.add(model1);

                                        }
                                        if (imageSyncModelList.size() > 0) {
                                            Intent intent = new Intent(context, UploadImageIntentService.class);
                                            intent.putExtra("id", model.getId());
                                            Gson gson1 = new Gson();
                                            String imageSyncJson = gson1.toJson(imageSyncModelList);
                                            intent.putExtra("imageSyncList", imageSyncJson);
                                            context.startService(intent);
                                        }

                                    }

                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                    String error = "";
                    if (call != null) {
                        error = call.toString();
                    }
                    Log.i("Error in image upload", error);
                }
            });

        }

    }

    private void syncImages() {
        List<ImageSyncModel> imageSyncModelList = new ArrayList<>();
        List<ImageSync> imageSyncList = DataBaseManager.getInstance().getImageListByStatus();
        if (imageSyncList.size() > 0) {
            List<ImageFIleModel> fIleModels = new ArrayList<>();
            ImageSyncModel model1 = new ImageSyncModel();
            for (int i = 0; i < imageSyncList.size(); i++) {
                ImageFIleModel imageFIleModel = new ImageFIleModel();
                imageFIleModel.setImageName(imageSyncList.get(i).getImageUrl());
                imageFIleModel.setFile(new File(imageSyncList.get(i).getImageUrl()));
                imageFIleModel.setImageParam(imageSyncList.get(i).getImageParam());
                fIleModels.add(imageFIleModel);
            }
            model1.setImageFIleModels(fIleModels);
            imageSyncModelList.add(model1);
            if (imageSyncModelList.size() > 0) {
                AttendanceModel model = DatabaaseUtils.getAttendanceDetailsByMobileId(context, sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.ATTENDANCE_MOBILE_ID));
                Intent intent = new Intent(context, UploadImageIntentService.class);
                intent.putExtra("id", model.getId());
                Gson gson1 = new Gson();
                String imageSyncJson = gson1.toJson(imageSyncModelList);
                intent.putExtra("imageSyncList", imageSyncJson);
                context.startService(intent);
            }

        }
    }
}
