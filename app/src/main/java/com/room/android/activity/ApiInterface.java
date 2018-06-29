package com.room.android.activity;


import com.room.android.PeristanceDatabase.Entity.models.ContactResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/api/provider/worldcountry")
    Call<List<ContactResponse>> doContactResponseDetails();
}
