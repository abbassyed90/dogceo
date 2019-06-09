package com.android.abbas.DogCEO.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.abbas.DogCEO.interfaces.OnClickSubBreed;
import com.android.abbas.DogCEO.OnClickViewHolder;
import com.android.abbas.DogCEO.R;
import com.android.abbas.DogCEO.database.entities.Breed;
import com.android.abbas.DogCEO.database.entities.SubBreed;

import java.util.ArrayList;
import java.util.List;

public class SubBreedAdapter extends RecyclerView.Adapter<SubBreedAdapter.SubBreedViewHolder> {

    private Breed breed;
    private List<SubBreed> subBreeds = new ArrayList<>();

    private OnClickSubBreed onClickSubBreed;

    @NonNull
    @Override
    public SubBreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final SubBreedViewHolder viewHolder = new SubBreedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chip_sub_breed, parent, false));
        viewHolder.itemView.setOnClickListener(new OnClickViewHolder(viewHolder) {
            @Override
            public void onClickItem(View view, int position) {
                if (onClickSubBreed != null) {
                    onClickSubBreed.onClickSubBreed(breed, getSubBreed(position));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubBreedViewHolder holder, int position) {
        holder.bind(subBreeds.get(position));
    }

    @Override
    public int getItemCount() {
        return subBreeds.size();
    }

    public void setOnClickSubBreed(OnClickSubBreed onClickSubBreed) {
        this.onClickSubBreed = onClickSubBreed;
    }

    public void add(final Breed breed, List<SubBreed> subBreeds) {
        this.subBreeds.clear();
        this.subBreeds.addAll(subBreeds);
        this.breed = new Breed(breed);
        notifyDataSetChanged();
    }

    public SubBreed getSubBreed(int position) {
        return new SubBreed(subBreeds.get(position));
    }

    public class SubBreedViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView;

        public SubBreedViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        public void bind(SubBreed subBreed) {
            textView.setText(subBreed.getName());
        }

    }
}
