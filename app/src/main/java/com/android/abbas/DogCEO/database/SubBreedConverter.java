package com.android.abbas.DogCEO.database;

import androidx.room.TypeConverter;

import com.android.abbas.DogCEO.database.entities.SubBreed;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubBreedConverter {

    @TypeConverter
    public String from(List<SubBreed> subBreeds) {
        if (subBreeds == null) {
            return (null);
        }

        final JSONArray subBreedsJsonArray = new JSONArray();
        for (int i = 0; i < subBreeds.size(); i++) {
            subBreedsJsonArray.put(subBreeds.get(i).getName());
        }

        return subBreedsJsonArray.toString();
    }

    @TypeConverter
    public List<SubBreed> too(String subBreedJsonArrayString) {
        if (subBreedJsonArrayString == null) {
            return Collections.emptyList();
        }

        final List<SubBreed> subBreeds = new ArrayList<>();

        try {
            JSONArray subBreedJsonArray = new JSONArray(subBreedJsonArrayString);
            for (int i = 0; i < subBreedJsonArray.length(); i++) {
                subBreeds.add(new SubBreed(subBreedJsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return subBreeds;
    }

}
