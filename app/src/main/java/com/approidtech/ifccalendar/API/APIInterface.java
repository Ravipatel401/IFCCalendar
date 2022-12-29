package com.approidtech.ifccalendar.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("calender_api")
    Call<ResponseData> fetchDate(@Field("api_key") String api_key, @Field("selected_date") String Selected_Date, @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("calender_api_365")
    Call<ResponseData> fethcDateIFC(@Field("api_key") String api_key, @Field("selected_date") String Selected_Date, @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("calender_events")
    Call<ResponseData> addEvent(@Field("api_key") String api_key, @Field("is_365") String is_365, @Field("is_date") String is_date, @Field("is_time") String is_time, @Field("message") String message, @Field("device_id") String device_id);


}
