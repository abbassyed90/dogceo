package com.android.abbas.DogCEO.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.abbas.DogCEO.database.entities.SubBreedImages;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SubBreedImageDao {

    @Query("SELECT * FROM sub_breed_image")
    LiveData<List<SubBreedImages>> getAll();

    @Query("SELECT * FROM sub_breed_image WHERE sub_breed_name = :name")
    SubBreedImages getSubBreed(String name);

    @Insert(onConflict = REPLACE)
    void insert(SubBreedImages breed);

    @Insert(onConflict = REPLACE)
    void insert(SubBreedImages... breedImages);

    @Delete
    void delete(SubBreedImages breedImages);

    @Update
    void update(SubBreedImages breadImage);

}
