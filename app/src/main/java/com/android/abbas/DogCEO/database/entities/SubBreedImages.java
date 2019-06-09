package com.android.abbas.DogCEO.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.abbas.DogCEO.database.ImageConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "sub_breed_image")
public class SubBreedImages {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sub_breed_name")
    private String breed;

    @TypeConverters(ImageConverter.class)
    private List<String> urls;

    public SubBreedImages() {
    }

    @Ignore
    public SubBreedImages(String breed, List<String> urls) {
        this.breed = breed;
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
