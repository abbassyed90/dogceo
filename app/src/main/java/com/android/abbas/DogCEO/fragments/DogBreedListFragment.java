package com.android.abbas.DogCEO.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.abbas.DogCEO.interfaces.OnClickDogBreed;
import com.android.abbas.DogCEO.interfaces.OnClickSubBreed;
import com.android.abbas.DogCEO.R;
import com.android.abbas.DogCEO.ViewModel.BreedViewModel;
import com.android.abbas.DogCEO.adapter.DogBreedAdapter;
import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.database.entities.SubBreed;

import java.util.List;

public class DogBreedListFragment extends BaseFragment implements OnClickDogBreed, OnClickSubBreed {

    private final DogBreedAdapter dogBreedAdapter = new DogBreedAdapter();

    private DividerItemDecoration dividerItemDecoration;

    private View progressBar;
    private RecyclerView recyclerView;

    public static DogBreedListFragment getInstance() {
        return new DogBreedListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BreedViewModel breedViewModel = ViewModelProviders.of(this).get(BreedViewModel.class);
        breedViewModel.getAllBreeds().observe(this, new Observer<List<Breed>>() {
            @Override
            public void onChanged(List<Breed> breeds) {
                displayProgressBar(false);
                updateAdapter(breeds);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dog_breed_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerview);

        setupRecyclerView(recyclerView);

        final BreedViewModel breedViewModel = ViewModelProviders.of(this).get(BreedViewModel.class);
        displayProgressBar(!breedViewModel.hasBreeds());
    }

    @Override
    public void onResume() {
        super.onResume();
        dogBreedAdapter.setOnClickDogBreed(this);
        dogBreedAdapter.setOnSubBreedClick(this);

        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onPause() {
        super.onPause();
        dogBreedAdapter.setOnClickDogBreed(null);
        dogBreedAdapter.setOnSubBreedClick(null);

        recyclerView.removeItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onClickBreed(Breed breed) {
        getNavigationHost().onSwitchFragment(BreedImageViewerFragment.getInstance(breed));
    }

    @Override
    public void onClickSubBreed(Breed breed, SubBreed subBreed) {
        getNavigationHost().onSwitchFragment(SubBreedImageViewFragment.getInstance(breed, subBreed));
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dogBreedAdapter);
        recyclerView.setHasFixedSize(true);

        this.dividerItemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL);
    }

    private void updateAdapter(List<Breed> breeds) {
        if (breeds != null) {
            dogBreedAdapter.add(breeds);
        }
    }

    private void displayProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
