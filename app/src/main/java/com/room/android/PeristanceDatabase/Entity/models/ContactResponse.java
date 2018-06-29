package com.room.android.PeristanceDatabase.Entity.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "contact_response")
public class ContactResponse {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = "dial_code")
    @SerializedName("dial_code")
    @Expose
    private String dialCode;

    @ColumnInfo(name = "currency_name")
    @SerializedName("currency_name")
    @Expose
    private String currencyName;

    @ColumnInfo(name = "currency_symbol")
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;

    @ColumnInfo(name = "currency_code")
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;

    @SerializedName("status")
    @Expose
    private Integer status;

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("timezone")
    @Expose
    private List<Timezone> timezone = new ArrayList<>();

    @Ignore
    @SerializedName("city")
    @Expose
    private List<City> city = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Timezone> getTimezone() {
        return timezone;
    }

    public void setTimezone(List<Timezone> timezone) {
        this.timezone = timezone;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

}
