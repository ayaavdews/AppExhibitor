package com.example.AppExhibitor.Api;

import com.example.AppExhibitor.Modul.ItemGuest;
import com.example.AppExhibitor.Modul.ItemLogin;
import com.example.AppExhibitor.Modul.ItemPengunjung;
import com.example.AppExhibitor.Modul.ItemSpinner1;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    //URL Untuk Bagian Login :
    public static final String URL_HEAD = "http://192.168.43.76/app_exhibitor/APIproject004/";

    //URL untuk bagian Purpose, Report :
    public static final String JSONURL = "http://192.168.43.76/app_exhibitor/project002/api/";

    //URL untuk bagian List Event, Scanner :
    public static final String URL = "http://192.168.43.76/app_exhibitor/project001/";

    @GET("event")
    Call<String> getJSONString();

    @GET("pengunjung")
    Call<String> getString();

    @FormUrlEncoded
    @POST("login.php")
    Call<ItemLogin> Login (@Field("email") String email,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST("events.php")
    Call<ItemSpinner1> GetDesc (@Field("events") String events);

    @FormUrlEncoded
    @POST("guest.php")
    Call<ItemGuest> Scann (@Field("id") String id);

    @FormUrlEncoded
    @POST("pengunjung")
    Call<ItemPengunjung> Save (@Field("exhibitor_name") String exhibitor_name,
                               @Field("exhibitor_stand") String exhibitor_stand,
                               @Field("guest_name") String guest_name,
                               @Field("guest_email") String guest_email,
                               @Field("visit_date") String visit_date,
                               @Field("visit_time") String visit_time,
                               @Field("purpose") String purpose);


}
