package com.android.abbas.DogCEO.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.abbas.DogCEO.database.ImageConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "breed_image")
public class BreedImages {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "breed_name")
    private String breed;

    @TypeConverters(ImageConverter.class)
    private List<String> urls;

    public BreedImages() {
    }

    public BreedImages(String name, List<String> urls) {
        this.breed = name;
        this.urls = new ArrayList<>(urls);
    }

    @NonNull
    public String getBreed() {
        return breed;
    }

    public void setBreed(@NonNull String breed) {
        this.breed = breed;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
