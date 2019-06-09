package com.android.abbas.DogCEO.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.database.entities.BreedImages;
import com.android.abbas.DogCEO.repository.BreedImageRepository;

public class BreedImageViewModel extends AndroidViewModel {

    private BreedImageRepository imageRepository;

    public BreedImageViewModel(@NonNull Application application) {
        super(application);
        imageRepository = new BreedImageRepository(application);
    }

    public MutableLiveData<BreedImages> getImages(Breed breed) {
        return imageRepository.getBreeds(breed);
    }
}
