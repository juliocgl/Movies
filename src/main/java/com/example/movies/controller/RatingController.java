package com.example.movies.controller;

import com.example.movies.exception.MovieNotFoundException;
import com.example.movies.model.Rating;
import com.example.movies.service.RatingService;
import com.example.movies.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller to manage requests related to ratings for the movies.
 */
@RestController
@RequestMapping("/api/movies/{movieId}/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final JwtTokenUtil jwtTokenUtil;

    private final Logger logger = LoggerFactory.getLogger(RatingController.class);

    public RatingController(RatingService ratingService, JwtTokenUtil jwtTokenUtil) {
        this.ratingService = ratingService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getRatingsByMovieId(@PathVariable String movieId) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Debugging getRatingsByMovieId method with parameter: movieId = %s", movieId));
        }
        try {
            return ResponseEntity.ok(ratingService.getRatingsByMovieId(Long.valueOf(movieId)));
        } catch (MovieNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Rating> rateMovie(@RequestHeader("Authorization") String bearerToken, @PathVariable String movieId, @RequestBody Rating rating) throws MovieNotFoundException {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Debugging rateMovie method with parameters: movieId = %s, rating = %s", movieId, rating));
        }
        rating.setUsername(jwtTokenUtil.getUsernameFromToken(bearerToken.substring(7)));
        Rating createdRating = ratingService.save(Long.valueOf(movieId), rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }
}
