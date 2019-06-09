package com.android.abbas.DogCEO.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.abbas.DogCEO.interfaces.OnClickDogBreed;
import com.android.abbas.DogCEO.interfaces.OnClickSubBreed;
import com.android.abbas.DogCEO.OnClickViewHolder;
import com.android.abbas.DogCEO.R;
import com.android.abbas.DogCEO.database.entities.Breed;

import java.util.ArrayList;
import java.util.List;

public class DogBreedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Breed> breeds = new ArrayList<>();
    private OnClickDogBreed onClickDogBreed;
    private OnClickSubBreed onClickSubBreed;

    private SparseArray<SubBreedAdapter> subBreedAdapterSparseArray = new SparseArray<>();

    private RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == R.layout.item_dog) {
            final DogBreedViewHolder viewHolder = new DogBreedViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dog, viewGroup, false));
            viewHolder.itemView.setOnClickListener(new OnClickViewHolder(viewHolder) {
                @Override
                public void onClickItem(View view, int position) {
                    if (onClickDogBreed != null) {
                        onClickDogBreed.onClickBreed(get(position));
                    }
                }
            });
            return viewHolder;
        } else {
            final DogSubBreedViewHolder viewHolder = new DogSubBreedViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dog_sub_breed, viewGroup, false));
            viewHolder.showAllButton.setOnClickListener(new OnClickViewHolder(viewHolder) {
                @Override
                public void onClickItem(View view, int position) {
                    if (onClickDogBreed != null) {
                        onClickDogBreed.onClickBreed(get(position));
                    }
                }
            });
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == R.layout.item_dog) {
            ((DogBreedViewHolder) holder).bind(breeds.get(position));
        } else {
            ((DogSubBreedViewHolder) holder).bind(breeds.get(position), subBreedAdapterSparseArray.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return breeds.get(position).hasSubBreeds() ? R.layout.item_dog_sub_breed : R.layout.item_dog;
    }

    @Override
    public int getItemCount() {
        return breeds.size();
    }

    public void add(List<Breed> breeds) {
        this.breeds.clear();
        this.breeds.addAll(breeds);

        this.subBreedAdapterSparseArray.clear();
        createSubBreedAdapters(breeds);

        this.notifyDataSetChanged();
    }

    private void createSubBreedAdapters(List<Breed> breeds) {
        for (int i = 0; i < breeds.size(); i++) {
            if (breeds.get(i).hasSubBreeds()) {
                final SubBreedAdapter subBreedAdapter = new SubBreedAdapter();
                subBreedAdapter.add(breeds.get(i), breeds.get(i).getSubBreeds());
                subBreedAdapter.setOnClickSubBreed(onClickSubBreed);
                subBreedAdapterSparseArray.put(i, subBreedAdapter);
            }
        }
    }

    public Breed get(int position) {
        return new Breed(breeds.get(position));
    }

    public void setOnClickDogBreed(OnClickDogBreed onClickDogBreed) {
        this.onClickDogBreed = onClickDogBreed;
    }

    public void setOnSubBreedClick(OnClickSubBreed onSubBreedClick) {
        this.onClickSubBreed = onSubBreedClick;
    }

    public class DogBreedViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public DogBreedViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.breedNameTextView);
        }

        public void bind(Breed breed) {
            textView.setText(breed.getName());
        }
    }

    public class DogSubBreedViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RecyclerView recyclerView;
        Button showAllButton;

        public DogSubBreedViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.subBreedNameTextView);
            recyclerView = itemView.findViewById(R.id.recyclerview);
            showAllButton = itemView.findViewById(R.id.showAllButton);

            recyclerView.setHasFixedSize(true);
            recyclerView.setRecycledViewPool(recycledViewPool);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }

        public void bind(Breed breed, SubBreedAdapter subBreedAdapter) {
            textView.setText(breed.getName());
            recyclerView.setAdapter(subBreedAdapter);
        }
    }
}
