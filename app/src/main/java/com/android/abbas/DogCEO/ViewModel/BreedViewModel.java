package com.android.abbas.DogCEO.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.repository.BreedRepository;

import java.util.List;

public class BreedViewModel extends AndroidViewModel {

    private final BreedRepository breedRepository;
    private LiveData<List<Breed>> breeds;

    public BreedViewModel(@NonNull Application application) {
        super(application);
        this.breedRepository = new BreedRepository(application);
    }

    public LiveData<List<Breed>> getAllBreeds() {
        breeds = breedRepository.getBreeds();
        return breeds;
    }

    public boolean hasBreeds() {
        return breeds.getValue() != null;
    }
}
