package com.example.movies.service;

import com.example.movies.exception.MovieNotFoundException;
import com.example.movies.model.Movie;

import java.util.List;

/**
 * Service which defines operations for movies.
 */
public interface MovieService {

    Movie get(String title) throws MovieNotFoundException;

    boolean isBestPictureWinner(String title) throws MovieNotFoundException;

    List<Movie> getTopRatedMovies();
}
