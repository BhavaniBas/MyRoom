package com.room.android.PeristanceDatabase.Entity.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Timezone {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("country_id")
    @Expose
    private String countryId;

    @SerializedName("country_code")
    @Expose
    private String countryCode;

    @SerializedName("coordinates")
    @Expose
    private String coordinates;

    @SerializedName("time_zone")
    @Expose
    private String timeZone;

    @SerializedName("comments")
    @Expose
    private String comments;

    @SerializedName("utc_offset")
    @Expose
    private String utcOffset;

    @SerializedName("utc_dst_offset")
    @Expose
    private String utcDstOffset;
    @SerializedName("notes")
    @Expose
    private String notes;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public void setUtcDstOffset(String utcDstOffset) {
        this.utcDstOffset = utcDstOffset;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


}
