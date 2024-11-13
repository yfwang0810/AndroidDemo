package yfwang.androiddemo.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import yfwang.androiddemo.global.MobileApplication;
import yfwang.androiddemo.utils.SharedpreferencesUtil;

/**
 * Description: 请求控制类
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2018/5/4 16:00
 */
public class HttpManager {
    private static final String TAG = "HttpManager";
    private OkHttpClient.Builder builder;
    private static final int DEFAULT_TIMEOUT = 20;
    private Retrofit mRetrofit;
    private static HttpManager instance;

    //单例
    public static HttpManager getInstance() {
        synchronized (HttpManager.class) {
            if (instance == null) {
                instance = new HttpManager();

            }
        }
        return instance;
    }


    public HttpManager() {
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);  //写入超时时间
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);   //读取超时时间
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, "log: " + message);
            }
        });
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);
        Interceptor postInterceptor = new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request.Builder requestBuilder = request.newBuilder();
                String token = SharedpreferencesUtil.getString(MobileApplication.getInstance().getBaseContext(), "token", "");
                if (!TextUtils.isEmpty(token)) {
                    requestBuilder.addHeader("auth_token", token);
                } else {
                    requestBuilder.addHeader("auth_token", "10000");
                }
                requestBuilder.addHeader("sign","");
                requestBuilder.addHeader("version","1.0");
                requestBuilder.addHeader("charset","UTF-8");
                requestBuilder.addHeader("deviceType","a");
                requestBuilder.addHeader("Accept-Language",MobileApplication.getInstance().getCurrLanguageEnv());
                requestBuilder.addHeader("w_appid","wh_wh3_android_"+MobileApplication.getInstance().getVersionName());//客户端版本描述
              
                return chain.proceed(requestBuilder.build());
            }
        };
        builder.addInterceptor(postInterceptor);

    }


    /**
     * 获取请求代理
     *
     * @param service
     * @param url
     * @param <T>
     * @return
     */
    public <T> T configRetrofit(Class<T> service, String url) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return mRetrofit.create(service);
    }



}
