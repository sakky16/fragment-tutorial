package com.trisysit.epc_android.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by trisys on 11/1/18.
 */

public class ServiceGenerator {
    private static volatile ServiceGenerator instance;
    public static ServiceGenerator getInstance() {
        ServiceGenerator localInstance = instance;
        if (localInstance == null) {
            synchronized (ServiceGenerator.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ServiceGenerator();
                }
            }
        }
        return localInstance;
    }
    OkHttpClient.Builder  client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS);

    private   Retrofit.Builder builder =
            new Retrofit.Builder()
                    .client(client.build())
                    .baseUrl(NetworkUtils.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private   Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public   <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
