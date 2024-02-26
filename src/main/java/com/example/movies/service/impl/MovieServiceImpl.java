package com.example.movies.service.impl;

import com.example.movies.exception.MovieNotFoundException;
import com.example.movies.model.Movie;
import com.example.movies.repository.MovieRepository;
import com.example.movies.service.ExternalAPIService;
import com.example.movies.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link MovieService}.
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final ExternalAPIService externalAPIService;

    public MovieServiceImpl(MovieRepository movieRepository, ExternalAPIService externalAPIService) {
        this.movieRepository = movieRepository;
        this.externalAPIService = externalAPIService;
    }

    @Override
    public Movie get(String title) throws MovieNotFoundException {
        return movieRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new MovieNotFoundException("Movie with title '" + title + "' not found"));
    }

    @Override
    public boolean isBestPictureWinner(String title) throws MovieNotFoundException {
        return movieRepository.findByTitleIgnoreCase(title).map(Movie::isBestPictureWinner)
                .orElseThrow(() -> new MovieNotFoundException("Movie with title '" + title + "' not found"));
    }

    @Override
    public List<Movie> getTopRatedMovies() {
        return movieRepository.findTop10RatedMoviesByAvgRating()
                .stream().peek(movie -> movie.setBoxOffice(getBoxOfficeOfMovie(movie.getTitle())))
                .sorted(Comparator.comparing(Movie::getBoxOffice).reversed())
                .collect(Collectors.toList());
    }

    private Long getBoxOfficeOfMovie(String title) {
        return externalAPIService.getMovieBoxOfficeFromExternalAPI(title);
    }
}
