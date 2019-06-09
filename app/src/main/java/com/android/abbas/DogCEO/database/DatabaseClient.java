package com.android.abbas.DogCEO.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private static final String DATABASE_NAME = "dog_breed";
    private static DatabaseClient mInstance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(context);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}