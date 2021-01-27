package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    String backdropPath;
    String posterPath;
    String title;
    String overview;
    Double voteAverage;

    // no-arg, empty constructor required for Parceler
    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = Double.parseDouble(jsonObject.getString("vote_average"));
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    // The correct way to implement this is to
    // - Fetch all the available image sizes from the Configuration API
    // - Append it to the available base url
    // - add the relative path posterPath to the url
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public static List<Object> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Object> movies = new ArrayList<>();

        for(int i = 0; i < movieJsonArray.length(); i++) {
            Movie movie = new Movie(movieJsonArray.getJSONObject(i));

            // If rating is greater than 5, add the backdrop path
            // Else, add the movie
            if (movie.getVoteAverage() > 5) {
                movies.add(movie.getBackdropPath());
            } else {
                movies.add(movie);
            }
        }

        return movies;
    }
}
