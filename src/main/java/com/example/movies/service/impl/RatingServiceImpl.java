package com.example.movies.service.impl;

import com.example.movies.exception.MovieNotFoundException;
import com.example.movies.model.Rating;
import com.example.movies.repository.MovieRepository;
import com.example.movies.repository.RatingRepository;
import com.example.movies.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link RatingService}.
 */
@Service
public class RatingServiceImpl implements RatingService {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> getRatingsByMovieId(Long movieId) throws MovieNotFoundException {
        if (!movieRepository.existsById(movieId)) {
            throw new MovieNotFoundException("Movie with id '" + movieId + "' not found");
        }
        return ratingRepository.findByMovieId(movieId);
    }

    @Override
    public Rating save(Long movieId, Rating rating) throws MovieNotFoundException {
        if (rating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }
        return movieRepository.findById(movieId).map(movie -> {
            rating.setMovie(movie);
            return ratingRepository.save(rating);
        }).orElseThrow(() -> new MovieNotFoundException("Movie with id '" + movieId + "' not found"));
    }
}
