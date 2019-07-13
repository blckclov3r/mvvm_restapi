package com.example.restapimvvm;

import android.app.Application;

import com.example.restapimvvm.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private Retrofit retrofit = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if(retrofit == null){
            synchronized (MyApplication.class) {
                if(retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
