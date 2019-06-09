package com.android.abbas.DogCEO.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.abbas.DogCEO.GridItemDecorator;
import com.android.abbas.DogCEO.R;
import com.android.abbas.DogCEO.ViewModel.SubBreedImageViewModel;
import com.android.abbas.DogCEO.adapter.ImageAdapter;
import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.database.entities.SubBreed;
import com.android.abbas.DogCEO.database.entities.SubBreedImages;

public class SubBreedImageViewFragment extends BaseFragment {

    private static final String BUNDLE_SELECTED_SUB_BREED = "bundle.selected_sub_breed";
    private static final String BUNDLE_SELECTED_BREED = "bundle.selected_breed";
    private static final int GRID_SPAN = 5;

    private GridItemDecorator gridItemDecorator;

    private final ImageAdapter adapter = new ImageAdapter();

    private View progressBar;
    private Breed breed;
    private SubBreed subBreed;
    private RecyclerView recyclerView;

    public static SubBreedImageViewFragment getInstance(Breed selectedBreed, SubBreed selectedSubBreed) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_SELECTED_SUB_BREED, selectedSubBreed);
        bundle.putParcelable(BUNDLE_SELECTED_BREED, selectedBreed);

        final SubBreedImageViewFragment fragment = new SubBreedImageViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        breed = getArguments().getParcelable(BUNDLE_SELECTED_BREED);
        subBreed = getArguments().getParcelable(BUNDLE_SELECTED_SUB_BREED);

        gridItemDecorator = new GridItemDecorator((int)getResources().getDimension(R.dimen.item_grid_spacing),GRID_SPAN);

        final SubBreedImageViewModel breedImageViewModel = ViewModelProviders.of(this).get(SubBreedImageViewModel.class);
        breedImageViewModel.getImages(breed, subBreed).observe(this, new Observer<SubBreedImages>() {
            @Override
            public void onChanged(SubBreedImages breedImages) {
                displayProgressBar(false);
                updateAdapter(breedImages);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_breed_image_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerview);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);

        setupToolBar(toolbar);
        setupRecyclerView(recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.addItemDecoration(gridItemDecorator);
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerView.removeItemDecoration(gridItemDecorator);
    }

    private void setupToolBar(Toolbar toolbar) {
        toolbar.setTitle(breed.getName());
        toolbar.setSubtitle(subBreed.getName());
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GRID_SPAN));
    }

    private void displayProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void updateAdapter(SubBreedImages breedImages) {
        if (breedImages != null) {
            adapter.add(breedImages.getUrls());
        }
    }

}
