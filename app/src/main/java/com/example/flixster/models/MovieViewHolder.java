package com.example.flixster.models;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;

import org.jetbrains.annotations.NotNull;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle;
    public TextView tvOverview;
    public ImageView ivPoster;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvOverview = itemView.findViewById(R.id.tvOverview);
        ivPoster = itemView.findViewById(R.id.ivPoster);
    }

    public void bind(Context context, @NotNull Movie movie) {
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        String imageUrl;
        int orientation = context.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getBackdropPath();
        } else {
            imageUrl = movie.getPosterPath();
        }

        Glide
                .with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(ivPoster);
    }
}
