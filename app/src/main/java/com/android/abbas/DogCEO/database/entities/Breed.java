package com.android.abbas.DogCEO.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.abbas.DogCEO.database.SubBreedConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "dog_breed")
public class Breed implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @TypeConverters(SubBreedConverter.class)
    @ColumnInfo(name = "sub_breeds")
    private List<SubBreed> subBreeds;

    public Breed() {
    }

    public Breed(Breed breed) {
        this(breed.getName(), breed.getSubBreeds());
    }

    public Breed(String breed, List<SubBreed> subBreeds) {
        this.name = breed;
        this.subBreeds = new ArrayList<>(subBreeds);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubBreed> getSubBreeds() {
        return subBreeds;
    }

    public void setSubBreeds(List<SubBreed> subBreeds) {
        this.subBreeds = subBreeds;
    }

    public boolean hasSubBreeds() {
        return subBreeds != null && !subBreeds.isEmpty();
    }

    protected Breed(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            subBreeds = new ArrayList<SubBreed>();
            in.readList(subBreeds, SubBreed.class.getClassLoader());
        } else {
            subBreeds = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (subBreeds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(subBreeds);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Breed> CREATOR = new Creator<Breed>() {
        @Override
        public Breed createFromParcel(Parcel in) {
            return new Breed(in);
        }

        @Override
        public Breed[] newArray(int size) {
            return new Breed[size];
        }
    };
}