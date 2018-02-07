package com.trisysit.epc_android.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.trisysit.epc_android.BuildConfig;
import com.trisysit.epc_android.SqliteDatabase.ActivityMaster;
import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.SqliteDatabase.LocationMaster;
import com.trisysit.epc_android.SqliteDatabase.ProjectTable;
import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.SqliteDatabase.SubActivityMaster;
import com.trisysit.epc_android.model.AttendanceModel;
import com.trisysit.epc_android.network.SyncApiClass;
import com.trisysit.epc_android.serverModel.AttendanceDataModel;
import com.trisysit.epc_android.serverModel.AttendanceRequest;
import com.trisysit.epc_android.serverModel.AttendanceResponse;
import com.trisysit.epc_android.serverModel.TaskDataModel;
import com.trisysit.epc_android.serverModel.TaskRequestModel;
import com.trisysit.epc_android.serverModel.TaskResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by trisys on 11/12/17.
 */

public class PullSyncManager {
    private Context mContext;
    private PullDataSyncListener dataSyncListener;
    private SharedPrefHelper sharedPrefHelper;
    private DataBaseManager dataBaseManager;

    public PullSyncManager(Context context,PullDataSyncListener pullDataSyncListener){
        this.mContext=context;
        this.dataSyncListener=pullDataSyncListener;
        sharedPrefHelper=SharedPrefHelper.getInstance(mContext);
        dataBaseManager=DataBaseManager.getInstance();

    }
    public void pullAllData(){
        PullDataAsyncTask pullDataAsyncTask=new PullDataAsyncTask();
        pullDataAsyncTask.execute();

    }
    public String pullAttendance(){
        HttpLoggingInterceptor interceptor=null;
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS);
        if(BuildConfig.DEBUG){
            interceptor=new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }

        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(NetworkUtils.SERVER_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String ispullSuccess="";
        String token = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.TOKEN);
        String lasySyncTime=sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_ATTENDANCE);
        List<AttendanceModel> attendanceModels = new ArrayList<>();
        SyncApiClass syncApiClass= retrofit.create(SyncApiClass.class);
        AttendanceRequest attendanceRequest=new AttendanceRequest();
        attendanceRequest.setToken(token);
        attendanceRequest.setAttendanceList(attendanceModels);
        attendanceRequest.setLastSyncTime(lasySyncTime);
        try {
            AttendanceResponse response=syncApiClass.syncAttendance(attendanceRequest).execute().body();
            if(response.getStatus().equalsIgnoreCase("200")){
                AttendanceDataModel dataModel=null;
                String lastSyncTime=response.getLastSyncTime();
                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_ATTENDANCE,lastSyncTime);
                  dataModel=response.getData();
                   attendanceModels=dataModel.getAttendanceList();
                  if(attendanceModels!=null){
                      if(attendanceModels.size()>0){
                          for (int i = 0; i < attendanceModels.size(); i++) {
                              if (DatabaaseUtils.isAttendanceExist(mContext, attendanceModels.get(i).getMobileId())) {

                                  DatabaaseUtils.replaceAttendance(mContext, attendanceModels.get(i));

                              } else {
                                  DatabaaseUtils.insertAttendance(mContext, attendanceModels.get(i));
                              }
                          }
                      }
                  }
                  ispullSuccess=AppUtils.SYNC_COMPLETED;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ispullSuccess;

    }
    public String pullTaskProject(){
        HttpLoggingInterceptor interceptor=null;

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS);
        if(BuildConfig.DEBUG){
            interceptor=new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }

        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(NetworkUtils.SERVER_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String ispullSuccess="";
        TaskDataModel dataModel;
        String token = sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.TOKEN);
        String lasySyncTime=sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_TASK);
        List<ProjectTask> taskList1=new ArrayList<>();
        SyncApiClass syncApiClass= retrofit.create(SyncApiClass.class);
        TaskRequestModel requestModel=new TaskRequestModel();
        requestModel.setToken(token);
        requestModel.setTasks(taskList1);
        requestModel.setLastSyncTime(lasySyncTime);
        try {
            TaskResponseModel model=syncApiClass.syncTask(requestModel).execute().body();
            if(model.getStatus().equalsIgnoreCase("200")){
                String lastSyncTime=model.getLastSyncTime();
                sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_TASK,lastSyncTime);
                dataModel=model.getData();
                if(dataModel!=null){
                     List<ProjectTask> taskList=dataModel.getTasks();
                     List<ProjectTable> projectList=dataModel.getProjects();
                     List<ActivityMaster> activityMasterList=dataModel.getActivities();
                     List<SubActivityMaster> subActivityMasterList=dataModel.getSubActivities();
                     List<LocationMaster> locationMasterList=dataModel.getLocationMasters();
                     if(projectList!=null && projectList.size()>0){
                         dataBaseManager.insertProjectList(projectList);
                     }
                     if(taskList!=null && taskList.size()>0){
                         dataBaseManager.insertTaskList(taskList);
                     }
                     if(activityMasterList!=null && activityMasterList.size()>0){
                         dataBaseManager.insertActivityList(activityMasterList);
                     }
                     if(subActivityMasterList!=null && subActivityMasterList.size()>0){
                         dataBaseManager.insertSubActivityList(subActivityMasterList);
                     }
                     if(locationMasterList!=null && locationMasterList.size()>0){
                         dataBaseManager.insertLocationMasterList(locationMasterList);
                     }
                    ispullSuccess=AppUtils.SYNC_COMPLETED;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ispullSuccess;
    }
    public class PullDataAsyncTask extends AsyncTask<String,Long,String>{

        @Override
        protected String doInBackground(String... strings) {
            String user_role=sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.ROLE_NAME);
            if(user_role.equalsIgnoreCase(AppUtils.ROLE_ATTENDANCE_ADMIN)){
                try {

                    String isAttendanceSynced=pullAttendance();
                    if(isAttendanceSynced.equalsIgnoreCase(AppUtils.SYNC_COMPLETED)){
                        publishProgress(100l);
                    }
                    else {
                        return null;
                    }
                    return "success";
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                try {
                    String isTaskSync=pullTaskProject();
                    if(isTaskSync.equalsIgnoreCase(AppUtils.SYNC_COMPLETED)){
                        publishProgress(50l);
                    }
                    else {
                        return null;
                    }
                    String isAttendanceSynced=pullAttendance();
                    if(isAttendanceSynced.equalsIgnoreCase(AppUtils.SYNC_COMPLETED)){
                        publishProgress(100l);
                    }
                    else {
                        return null;
                    }
                    return "success";
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                if(s.equalsIgnoreCase("success")){
                    dataSyncListener.onPullDataSyncCompleted(true);
                }
                else {
                    dataSyncListener.onPullDataSyncCompleted(false);
                }
            }
            else {
                dataSyncListener.onPullDataSyncCompleted(false);
            }
        }
    }
    public interface PullDataSyncListener{
        void onPullProgressUpdate(int percentage);

        void onPullDataSyncCompleted(boolean status);
    }
}