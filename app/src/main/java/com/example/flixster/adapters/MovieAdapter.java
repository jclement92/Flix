package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.activities.MovieDetailActivity;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int POSTER = 0, BACKDROP = 1;
    Context context;
    List<Object> movies;

    public MovieAdapter(Context context, List<Object> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (viewType) {
            case BACKDROP:
                View backdropView = layoutInflater.inflate(R.layout.item_backdrop, parent, false);
                viewHolder = new BackdropViewHolder(backdropView);
                break;
            case POSTER:
            default:
                View cardView = layoutInflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new MovieViewHolder(cardView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        Object movie = movies.get(position);

        switch (holder.getItemViewType()) {
            case BACKDROP:
                BackdropViewHolder backdropViewHolder = (BackdropViewHolder) holder;
                backdropViewHolder.bind((String) movie);
                break;
            case POSTER:
            default:
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                movieViewHolder.bind((Movie) movie);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = movies.get(position);

        if (object instanceof Movie) {
            return POSTER;
        } else if (object instanceof String) {
            return BACKDROP;
        }
        return -1;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;
        public TextView tvOverview;
        public ImageView ivPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            // add this as the itemView's OnClickListener
            itemView.setOnClickListener(this);
        }

        public void bind(@NotNull Movie movie) {
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

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
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Movie movie = (Movie) movies.get(position);
                Intent intent = new Intent(context, MovieDetailActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, tvTitle, "title");
                context.startActivity(intent, optionsCompat.toBundle());
            }
        }
    }

    public class BackdropViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivBackdrop;

        public BackdropViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackdrop = itemView.findViewById(R.id.ivBackdrop);
            itemView.setOnClickListener(this);
        }

        public void bind(@NonNull String movie) {
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            Glide
                    .with(context)
                    .load(movie)
                    .placeholder(R.drawable.placeholder)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivBackdrop);
        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//
//            if (position != RecyclerView.NO_POSITION) {
//                Object movie = movies.get(position);
//
//            }
        }
    }
}
