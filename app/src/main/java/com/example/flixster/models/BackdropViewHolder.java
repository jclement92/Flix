package com.example.flixster.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;

public class BackdropViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivBackdrop;

    public BackdropViewHolder(@NonNull View itemView) {
        super(itemView);
        ivBackdrop = itemView.findViewById(R.id.ivBackdrop);
    }

    public ImageView getImageView() {
        return ivBackdrop;
    }

    public void setImageView(ImageView ivBackdrop) {
        this.ivBackdrop = ivBackdrop;
    }

    public void configureBackDropViewHolder(Context context, String movie) {
        Glide
                .with(context)
                .load(movie)
                .placeholder(R.drawable.placeholder)
                .into(getImageView());
    }
}
