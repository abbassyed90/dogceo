package com.android.abbas.DogCEO.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.abbas.DogCEO.database.entities.Breed;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BreedDao {

    @Query("SELECT * FROM dog_breed")
    LiveData<List<Breed>> getAll();

    @Insert(onConflict = REPLACE)
    void insert(Breed breed);

    @Insert(onConflict = REPLACE)
    void insert(Breed... breed);

    @Delete
    void delete(Breed breed);

    @Update
    void update(Breed bread);

}
