package com.example.movies.controller;

import com.example.movies.exception.MovieNotFoundException;
import com.example.movies.model.Rating;
import com.example.movies.service.RatingService;
import com.example.movies.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class RatingControllerTest {
    @Mock
    private RatingService ratingService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRatingsByMovieId_ReturnsRatings_WhenMovieIdExists() throws MovieNotFoundException {
        String movieId = "123";
        List<Rating> expectedRatings = Arrays.asList(new Rating(), new Rating());
        when(ratingService.getRatingsByMovieId(Long.valueOf(movieId))).thenReturn(expectedRatings);

        ResponseEntity<List<Rating>> response = ratingController.getRatingsByMovieId(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRatings, response.getBody());
        verify(ratingService, times(1)).getRatingsByMovieId(Long.valueOf(movieId));
    }

    @Test
    void getRatingsByMovieId_ReturnsNotFound_WhenMovieIdDoesNotExist() throws MovieNotFoundException {
        String movieId = "1234";
        when(ratingService.getRatingsByMovieId(Long.valueOf(movieId)))
                .thenThrow(new MovieNotFoundException("Movie not found"));

        ResponseEntity<List<Rating>> response = ratingController.getRatingsByMovieId(movieId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ratingService, times(1)).getRatingsByMovieId(Long.valueOf(movieId));
    }

    @Test
    void rateMovie_ReturnsCreated_WhenRatingIsSavedSuccessfully() throws MovieNotFoundException {
        String token="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcwMjMxMTI5OCwiZXhwIjoxNzAyMzI5Mjk4fQ.ih9nC5TJVbA6cMLGaFpU2b7veZQ9gOSbaluFO53kCS9nU0WRZS-c93cfmiRu7FdpvGqngszuuOmbdaOLUuM_NA";
        String movieId = "456";
        Rating ratingToSave = new Rating();
        Rating savedRating = new Rating();
        when(ratingService.save(Long.valueOf(movieId), ratingToSave)).thenReturn(savedRating);

        ResponseEntity<Rating> response = ratingController.rateMovie(token, movieId, ratingToSave);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedRating, response.getBody());
        verify(ratingService, times(1)).save(Long.valueOf(movieId), ratingToSave);
    }

    @Test
    void rateMovie_ReturnsBadRequest_WhenMovieIdDoesNotExist() throws MovieNotFoundException {
        String token="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcwMjMxMTI5OCwiZXhwIjoxNzAyMzI5Mjk4fQ.ih9nC5TJVbA6cMLGaFpU2b7veZQ9gOSbaluFO53kCS9nU0WRZS-c93cfmiRu7FdpvGqngszuuOmbdaOLUuM_NA";
        String movieId = "123";
        Rating ratingToSave = new Rating();
        when(ratingService.save(Long.valueOf(movieId), ratingToSave))
                .thenThrow(new MovieNotFoundException("Movie not found"));

        ResponseEntity<Rating> response = ratingController.rateMovie(token, movieId, ratingToSave);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(ratingService, times(1)).save(Long.valueOf(movieId), ratingToSave);
    }
}