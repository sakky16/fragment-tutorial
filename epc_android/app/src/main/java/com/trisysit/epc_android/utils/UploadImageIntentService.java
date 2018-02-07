package com.trisysit.epc_android.utils;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trisysit.epc_android.SqliteDatabase.ImageSync;
import com.trisysit.epc_android.model.ImageFIleModel;
import com.trisysit.epc_android.model.ImageSyncModel;
import com.trisysit.epc_android.network.SyncApiClass;
import com.trisysit.epc_android.network.UploadImage;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by trisys on 22/12/17.
 */

public class UploadImageIntentService extends IntentService {
    private List<ImageSyncModel> imageSyncModels;
    private String id;
    private static final String TAG = "imageUpload";
    int requestCounter;
    int responseCounter=0;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadImageIntentService(String name) {
        super(name);
    }
    public UploadImageIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        id=intent.getStringExtra("id");
        String imageSyncList=intent.getStringExtra("imageSyncList");
        Gson gson = new Gson();
        Type type = new TypeToken<List<ImageSyncModel>>() {
        }.getType();
        imageSyncModels = gson.fromJson(imageSyncList, type);
        if(imageSyncModels!=null && imageSyncModels.size()>0){
            for(int i=0;i<imageSyncModels.size();i++){
                if(imageSyncModels.get(i).getImageFIleModels().size()>0){
                    for(ImageFIleModel model:imageSyncModels.get(i).getImageFIleModels()){
                        callMultipleSyncImage(model.getFile(),model.getImageName(),model.getImageParam());
                    }
                }
            }
        }
    }
    private void callMultipleSyncImage(File imageFIle, String imageName,long imageParam){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS).build();
        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(NetworkUtils.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ++requestCounter;
        final String requestCouneter=String.valueOf(requestCounter);
        Log.d(TAG,requestCouneter);
        SyncApiClass apiClass=ServiceGenerator.getInstance().createService(SyncApiClass.class);
        String token= SharedPrefHelper.getInstance(UploadImageIntentService.this).getFromSharedPrefs(SharedPrefHelper.TOKEN);
        final RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),imageFIle);
        MultipartBody.Part part=MultipartBody.Part.createFormData("imageFIle",imageFIle.getName(),requestBody);
        Call<Object> resultCall=apiClass.callSyncImage(id,imageName,String.valueOf(imageParam),token,part);
        resultCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
               int responseCode=response.code();
                if(responseCode==200){
                    if(response.body()!=null){
                        ++responseCounter;
                        String responseCounter_str=String.valueOf(responseCounter);
                        Log.d(TAG,responseCounter_str);
                        String data=new Gson().toJson(response.body());
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            String status=jsonObject.getString("status");
                            String imageUrl=jsonObject.getString("message");
                            if(status.equalsIgnoreCase("200.0")){
                                ImageSync sync=DataBaseManager.getInstance().getImageSyncByImageUrl(imageUrl);
                                if(sync!=null){
                                    sync.setStatus(AppUtils.IMAGE_SYNCED);
                                    DataBaseManager.getInstance().insertImage(sync);
                                }
                            }
                            else if(status.equalsIgnoreCase("500.0")){
                                File file=new File(imageUrl);
                                long size=file.length();
                                callMultipleSyncImage(file,imageUrl,size);
                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else if(responseCode==500){
                    Toast.makeText(UploadImageIntentService.this,"Internal server error",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String error="";
                if(call!=null){
                     error=call.toString();
                }
                Log.i("Error in image upload",error);

            }
        });
    }

}
