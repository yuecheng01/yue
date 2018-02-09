package com.yuecheng.yue.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yuecheng.yue.util.NetWorkUtil;
import com.yuecheng.yue.util.YUE_AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/12/24.
 */

public class ApiServicesManager {
    private static ApiServicesManager mApiManage;

    private ApiServicesManager() {
    }

    public static ApiServicesManager getInstence() {
        if (mApiManage == null) {
            synchronized (ApiServicesManager.class) {
                if (mApiManage == null) {
                    mApiManage = new ApiServicesManager();
                }
            }
        }
        return mApiManage;
    }
    //定义拦截器
    private static final Interceptor INTERNET_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response mResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(YUE_AppUtils.getAppContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return mResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                // 离线时缓存保存1天
                int maxStale = 60 * 60 * 24 * 1;
                return mResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    //缓存目录
    private static File mHttpCacheDirectory = new File(YUE_AppUtils.getAppContext().getCacheDir(), "yueCache");
    //缓存大小为10M
    private static long mChacheSize = 10 * 1024 * 1024; //10MB
    private static Cache mCache = new Cache(mHttpCacheDirectory, mChacheSize);
    private static OkHttpClient mClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(INTERNET_INTERCEPTOR)
            .addInterceptor(INTERNET_INTERCEPTOR)
            .cache(mCache)
            .readTimeout(9, TimeUnit.SECONDS)
            .connectTimeout(9, TimeUnit.SECONDS)
            .build();

    private YUE_API yueapi;
    //我的电脑服务器
//    private String mBaseUrl = "http://yuecheng.xicp.io:14475/";
    //公司电脑服务器
    private String mBaseUrl = "http://192.168.3.130/";
    private Object mYueMonitor = new Object();
    private Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public YUE_API getYueapi() {
        if (null == yueapi) {
            synchronized (mYueMonitor) {
                if (null == yueapi) {
                    yueapi = new Retrofit.Builder()
                            .baseUrl(mBaseUrl)
                            .client(mClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()
                            .create(YUE_API.class);
                }
            }
        }
        return yueapi;
    }
}
