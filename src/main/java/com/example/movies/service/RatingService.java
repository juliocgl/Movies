package com.example.movies.service;

import com.example.movies.exception.MovieNotFoundException;
import com.example.movies.model.Rating;

import java.util.List;

/**
 * Service which defines operations for ratings for the movies.
 */
public interface RatingService {

    List<Rating> getRatingsByMovieId(Long movieId) throws MovieNotFoundException;

    Rating save(Long movieId, Rating rating) throws MovieNotFoundException;
}
