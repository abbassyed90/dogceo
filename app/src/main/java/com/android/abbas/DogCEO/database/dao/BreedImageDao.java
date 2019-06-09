package com.android.abbas.DogCEO.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.abbas.DogCEO.database.entities.BreedImages;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BreedImageDao {

    @Query("SELECT * FROM breed_image")
    LiveData<List<BreedImages>> getAll();

    @Query("SELECT * FROM breed_image WHERE breed_name = :breed")
    BreedImages getBreedImages(String breed);

    @Insert(onConflict = REPLACE)
    void insert(BreedImages breed);

    @Insert(onConflict = REPLACE)
    void insert(BreedImages... breedImages);

    @Delete
    void delete(BreedImages breedImages);

    @Update
    void update(BreedImages breadImage);

}
