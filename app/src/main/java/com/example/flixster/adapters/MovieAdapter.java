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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.activities.MovieDetailActivity;
import com.example.flixster.databinding.ItemBackdropBinding;
import com.example.flixster.databinding.ItemMovieBinding;
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
                ((BackdropViewHolder) holder).binding.setVariable(BR.movie, movie);
                ((BackdropViewHolder) holder).binding.executePendingBindings();
                backdropViewHolder.bind((String) movie);
                break;
            case POSTER:
            default:
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                ((MovieViewHolder) holder).binding.setMovieAdapter((Movie) movie);
                ((MovieViewHolder) holder).binding.executePendingBindings();
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
        final ItemMovieBinding binding;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemMovieBinding.bind(itemView);

            // add this as the itemView's OnClickListener
            itemView.setOnClickListener(this);
        }

        public void bind(@NotNull Movie movie) {
            String imageUrl;
            int orientation = context.getResources().getConfiguration().orientation;

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }

            BindingAdapterUtils.loadImage(binding.ivPoster, imageUrl);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Movie movie = (Movie) movies.get(position);
                Intent intent = new Intent(context, MovieDetailActivity.class);

                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));

                // Attach shared transition
                ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                (Activity) context,
                                binding.tvTitle,
                                "title");

                context.startActivity(intent, optionsCompat.toBundle());
            }
        }
    }

    public static class BackdropViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ItemBackdropBinding binding;

        public BackdropViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBackdropBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(@NonNull String movie) {
            BindingAdapterUtils.loadImage(binding.ivBackdrop, movie);
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

    public static class BindingAdapterUtils {
        @BindingAdapter({"imageUrl"})
        public static void loadImage(ImageView view, String url) {
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

            Glide
                    .with(view.getContext())
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(view);
        }
    }
}
