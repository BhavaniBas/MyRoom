package com.room.android.PeristanceDatabase.Database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.room.android.PeristanceDatabase.Dao.ContactResponseDao;
import com.room.android.PeristanceDatabase.Entity.models.City;
import com.room.android.PeristanceDatabase.Entity.models.ContactResponse;


@Database(entities = {ContactResponse.class,City.class},version = 1,exportSchema = false)
@TypeConverters({ContactResponseDao.Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactResponseDao getContactResponseDao();

}
