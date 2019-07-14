package com.example.restapimvvm;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.example.restapimvvm.util.Constants;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private static final String COMMON_TAG ="mAppLog";

    private Retrofit retrofit = null;
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_PRAGMA = "Pragma";

    private static final long cacheSize = 5 * 1024 * 1024; //5mb

    @Override
    public void onCreate() {
        super.onCreate();
        if(retrofit == null){
            synchronized (MyApplication.class) {
                if(retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .client(okHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }

    private  OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(httpLoggingInterceptor())
                .addNetworkInterceptor(networkInterceptor())
                .addInterceptor(offlineInterceptor())
                .build();
    }
    private Cache cache(){
        return new Cache(new File(getCacheDir(),"someIdentifier"), cacheSize);
    }

    //This interceptor will be called both if the network is available and if the network is not available
    private  Interceptor offlineInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept( Chain chain) throws IOException {
                Request request = chain.request();
                if(!hasNetwork()){
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();
                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }


    //This interceptor will be called only if the network is available
    private  Interceptor networkInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept( Chain chain) throws IOException {
                Log.d(COMMON_TAG,TAG+" networkInterceptor: called");

                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(5, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL,cacheControl.toString())
                        .build();

            }
        };
    }

    private  HttpLoggingInterceptor httpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log( String message) {
                        Log.d(COMMON_TAG,TAG+" httpLoggingInterceptor: "+message);
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public  boolean hasNetwork() {
        return isNetworkConnected();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
