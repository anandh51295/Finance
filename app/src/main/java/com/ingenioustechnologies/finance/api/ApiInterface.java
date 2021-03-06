package com.ingenioustechnologies.finance.api;

import com.ingenioustechnologies.finance.model.CheckRes;
import com.ingenioustechnologies.finance.model.CustomerRes;
import com.ingenioustechnologies.finance.model.LocRes;
import com.ingenioustechnologies.finance.model.LoginRes;
import com.ingenioustechnologies.finance.model.TrackRes;
import com.ingenioustechnologies.finance.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("api/authentication/login")
    @FormUrlEncoded
    Call<LoginRes> performlogin(@Field("username") String username, @Field("password") String password, @Field("X-API-KEY") String keys);

    @GET("tracker")
    Call<TrackRes> performtrack(@Query("userid") int userid, @Query("latitude") double latitude,@Query("longitude") double longitude);

    @GET("tracker")
    Call<TrackRes> performstrack(@Query("userid") int userid, @Query("latitude") String latitude,@Query("longitude") String longitude);

    @GET("userverify")
    Call<TrackRes> performverify(@Query("userid") int userid,@Query("cname")String cname,@Query("latitude") double latitude,@Query("longtitude") double longtitude);

    @GET("userlist")
    Call<UserRes>performalluser();

    @GET("verifylist")
    Call<CustomerRes>performallverifyuser();

    @GET("checklist")
    Call<CheckRes>performcheck(@Query("type")String type, @Query("val")String val);

    @GET("usertracker")
    Call<LocRes> performlocation(@Query("userid") int userid,@Query("type") String type,@Query("sdate") String sdate,@Query("edate") String edate);

    @GET("currenttracker")
    Call<LocRes> performcurrentlocation(@Query("userid") int userid);
}
