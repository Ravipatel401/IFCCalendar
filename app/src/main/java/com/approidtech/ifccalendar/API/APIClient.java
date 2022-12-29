package com.approidtech.ifccalendar.API;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String BASE_URL = "https://app.visit-tasmania.com.au/";
    public static final String API_KEY = "hTM5v9rw6gu3EpW1YqfNWwe9Rn5y2i";

    public static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
