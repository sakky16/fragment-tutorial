package com.trisysit.epc_android.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trisysit.epc_android.model.ImageFIleModel;
import com.trisysit.epc_android.model.ImageSyncModel;
import com.trisysit.epc_android.utils.NetworkUtils;
import com.trisysit.epc_android.utils.SharedPrefHelper;

import org.json.JSONObject;

import java.io.File;
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
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by trisys on 8/12/17.
 */

public class UploadImage {
    private Context context;
    private File imageFile;
    private List<File> imageFileList;
    private List<ImageSyncModel> imageSyncModels;
    private String id;

    public UploadImage(Context context, List<ImageSyncModel> imageFileList, String id){
        this.context=context;
        this.imageSyncModels=imageFileList;
        this.id=id;
    }

    public void syncImage() {
        if(imageSyncModels!=null && imageSyncModels.size()>0){
            for(int i=0;i<imageSyncModels.size();i++){
                if(imageSyncModels.get(i).getImageFIleModels().size()>0){
                    for(ImageFIleModel model:imageSyncModels.get(i).getImageFIleModels()){
                        callMultipleSyncImage(model.getFile(),model.getImageName());
                    }
                }

            }
        }

    }
    private void callMultipleSyncImage(File imageFIle,String imageName){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30000, TimeUnit.SECONDS)
                .readTimeout(30000,TimeUnit.SECONDS).build();
        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(NetworkUtils.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ImageInterface imageInterface=retrofit.create(ImageInterface.class);
        String token= SharedPrefHelper.getInstance(context).getFromSharedPrefs(SharedPrefHelper.TOKEN);
        final RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),imageFIle);
        MultipartBody.Part part=MultipartBody.Part.createFormData("imageFIle",imageFIle.getName(),requestBody);
        Call<Object> resultCall=imageInterface.callSyncImage(id,imageName,token,part);
        resultCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int responseCode=response.code();
                if(responseCode==200){
                    if(response.body()!=null){
                        String data=new Gson().toJson(response.body());
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            String status=jsonObject.getString("status");
                            String message=jsonObject.getString("message");
                            if(status.equalsIgnoreCase("200")){
                                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else if(responseCode==500){
                    Toast.makeText(context,"Internal server error",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String error=call.toString();
                Log.i("Error in image upload",error);

            }
        });
    }

    public interface ImageInterface {
        @Multipart
        @POST(NetworkUtils.IMAGE_SYNC)
        Call<Object> callSyncImage(@Header("Id")String id, @Header("imageUrl") String imageName,
                                   @Header("token") String token,
                                   @Part MultipartBody.Part file);
    }

}
