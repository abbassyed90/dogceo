package com.android.abbas.DogCEO.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.database.entities.SubBreed;
import com.android.abbas.DogCEO.database.entities.SubBreedImages;
import com.android.abbas.DogCEO.repository.SubBreedImageRepository;

public class SubBreedImageViewModel extends AndroidViewModel {

    private SubBreedImageRepository imageRepository;

    public SubBreedImageViewModel(@NonNull Application application) {
        super(application);
        imageRepository = new SubBreedImageRepository(application);
    }

    public MutableLiveData<SubBreedImages> getImages(Breed breed, SubBreed subBreed) {
        return imageRepository.getBreeds(breed, subBreed);
    }

}
