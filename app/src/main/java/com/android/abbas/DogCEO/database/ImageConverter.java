package com.android.abbas.DogCEO.database;

import androidx.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageConverter {

    @TypeConverter
    public String from(List<String> urls) {
        if (urls == null) {
            return (null);
        }

        final JSONArray urlJsonArray = new JSONArray();
        for (int i = 0; i < urls.size(); i++) {
            urlJsonArray.put(urls.get(i));
        }

        return urlJsonArray.toString();
    }

    @TypeConverter
    public List<String> too(String urlsJsonArrayString) {
        if (urlsJsonArrayString == null) {
            return Collections.emptyList();
        }

        final List<String> urls = new ArrayList<>();

        try {
            JSONArray urlJsonArray = new JSONArray(urlsJsonArrayString);
            for (int i = 0; i < urlJsonArray.length(); i++) {
                urls.add(urlJsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return urls;
    }

}
