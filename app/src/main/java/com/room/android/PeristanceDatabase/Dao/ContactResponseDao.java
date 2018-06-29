package com.room.android.PeristanceDatabase.Dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.room.android.PeristanceDatabase.Entity.models.City;
import com.room.android.PeristanceDatabase.Entity.models.ContactResponse;
import com.room.android.PeristanceDatabase.Entity.models.Timezone;

import java.lang.reflect.Type;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContactResponseDao {

    @Query("SELECT * FROM my_city")
    List<City> getCity();

    @Delete
    void deleteAll(List<ContactResponse> mDeleteCity);

    @Query("SELECT * FROM my_city ORDER BY id DESC")
    List<City> getDescCity();

    @Query("SELECT * FROM my_city")
    List<City> getSelectCityCount();

    @Query("SELECT * FROM my_city LIMIT 3")
    List<City> getCityLimit();

    @Insert(onConflict = REPLACE)
    void insertCountry(List<ContactResponse> response);

    @Insert(onConflict = REPLACE)
    void insertCityList(List<City> cityList);

    @Query("DELETE FROM my_city WHERE id = :id")
    void deleteUsers(int id);

    @Query("UPDATE my_city SET countryID = :countryID, cityName= :cityName WHERE id = :id")
    int updateCityUsers(String countryID,String cityName,int id);


    default void insertCountryWithCity(List<ContactResponse> response) {

        List<City> cityList = response.get(0).getCity();
        for (int i = 0; i < cityList.size(); i++) {
            cityList.get(i).setId(cityList.get(i).getId());
        }
        insertCityList(cityList);
        insertCountry(response);
    }

    class Converter {


        /*Time Zone Data*/

        @TypeConverter // note this annotation
        public String fromTimeZoneList(List<Timezone> optionValues) {
            if (optionValues == null) {
                return (null);
            }
            Gson gson = new Gson();
            Type type = new TypeToken<List<Timezone>>() {
            }.getType();
            String json = gson.toJson(optionValues, type);
            return json;
        }

        @TypeConverter // note this annotation
        public List<Timezone> toTimeZoneList(String optionValuesString) {
            if (optionValuesString == null) {
                return (null);
            }
            Gson gson = new Gson();
            Type type = new TypeToken<List<Timezone>>() {
            }.getType();
            List<Timezone> productCategoriesList = gson.fromJson(optionValuesString, type);
            return productCategoriesList;
        }


        /*City Data*/

        @TypeConverter // note this annotation
        public String fromOptionsCityList(List<City> optionValues) {
            if (optionValues == null) {
                return (null);
            }
            Gson gson = new Gson();
            Type type = new TypeToken<List<City>>() {
            }.getType();
            String json = gson.toJson(optionValues, type);
            return json;
        }

        @TypeConverter // note this annotation
        public List<City> toOptionsCityList(String optionValuesString) {
            if (optionValuesString == null) {
                return (null);
            }
            Gson gson = new Gson();
            Type type = new TypeToken<List<City>>() {
            }.getType();
            List<City> productCategoriesList = gson.fromJson(optionValuesString, type);
            return productCategoriesList;
        }
    }
}
