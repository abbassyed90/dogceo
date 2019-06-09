package com.android.abbas.DogCEO.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class SubBreed implements Parcelable {

    private String name;

    public SubBreed(String name) {
        this.name = name;
    }

    public SubBreed(SubBreed subBreed) {
        this.name = subBreed.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected SubBreed(Parcel in) {
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Creator<SubBreed> CREATOR = new Creator<SubBreed>() {
        @Override
        public SubBreed createFromParcel(Parcel in) {
            return new SubBreed(in);
        }

        @Override
        public SubBreed[] newArray(int size) {
            return new SubBreed[size];
        }
    };
}