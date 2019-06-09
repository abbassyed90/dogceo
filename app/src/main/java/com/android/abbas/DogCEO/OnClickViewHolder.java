package com.android.abbas.DogCEO;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class OnClickViewHolder implements View.OnClickListener {

    private final RecyclerView.ViewHolder viewHolder;

    public OnClickViewHolder(RecyclerView.ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    @Override
    public void onClick(View v) {
        if (viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
            onClickItem(v, viewHolder.getAdapterPosition());
        }
    }

    public abstract void onClickItem(View view, int position);

}
