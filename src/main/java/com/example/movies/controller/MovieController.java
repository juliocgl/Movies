package com.example.movies.controller;

import com.example.movies.exception.MovieNotFoundException;
import com.example.movies.model.Movie;
import com.example.movies.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest controller to manage requests related to movies.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    private final Logger logger = LoggerFactory.getLogger(MovieController.class);

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/title={title}")
    public ResponseEntity<Movie> getByTitle(@PathVariable String title) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Debugging getByTitle method with parameter: title = %s", title));
        }
        try {
            return ResponseEntity.ok(movieService.get(title));
        } catch (MovieNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/isBestPicture={title}")
    public ResponseEntity<Boolean> isBestPictureWinner(@PathVariable String title) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Debugging isBestPictureWinner method with parameter: title = %s", title));
        }
        try {
            return ResponseEntity.ok(movieService.isBestPictureWinner(title));
        } catch (MovieNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/topRated")
    public ResponseEntity<List<Movie>> getTopRatedMovies() {
        if (logger.isDebugEnabled()) {
            logger.debug("Debugging getTopRatedMovies method");
        }
        return ResponseEntity.ok(movieService.getTopRatedMovies());
    }
}
