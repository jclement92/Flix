package com.example.flixster.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flixster.R;
import com.example.flixster.models.BackdropViewHolder;
import com.example.flixster.models.Movie;
import com.example.flixster.models.MovieViewHolder;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Object> movies;

    private final int CARD = 0, BACKDROP = 1;

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
            case CARD:
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
                backdropViewHolder.configureBackDropViewHolder(context, (String) movie);
                break;
            case CARD:
            default:
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                movieViewHolder.bind(context, (Movie) movie);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position) instanceof Movie) {
            return CARD;
        } else if (movies.get(position) instanceof String) {
            return BACKDROP;
        }
        return -1;
    }
}
