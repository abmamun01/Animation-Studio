package com.mamunsproject.animationstudio.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String BASE_URL="https://www.googleapis.com/youtube/v3/";
    public static Retrofit retrofit=null;
    public static Retrofit getApiClient(){
        if (retrofit==null){
            Gson gson=new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit=new Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();

        }

        return retrofit;

    }
}
