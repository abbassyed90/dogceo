package com.android.abbas.DogCEO.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.abbas.DogCEO.database.dao.BreedDao;
import com.android.abbas.DogCEO.database.dao.BreedImageDao;
import com.android.abbas.DogCEO.database.dao.SubBreedImageDao;
import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.database.entities.BreedImages;
import com.android.abbas.DogCEO.database.entities.SubBreedImages;

@Database(entities = {Breed.class, BreedImages.class, SubBreedImages.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BreedDao breedDao();

    public abstract BreedImageDao breedImageDao();

    public abstract SubBreedImageDao subBreedImageDao();
}

