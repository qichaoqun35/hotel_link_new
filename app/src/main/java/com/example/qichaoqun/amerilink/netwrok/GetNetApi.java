package com.example.qichaoqun.amerilink.netwrok;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public class GetNetApi {

    /**
     * 获取位置网络请求的映射类
     * @return 映射类
     */
    public static NetApi getNetApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.map.baidu.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(NetApi.class);
    }

    /**
     * 获取api的实例对象
     * @param baseUrl 基本url
     * @return 实例对象
     */
    public static NetApi getNetApi(String baseUrl){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(NetApi.class);
    }
}
