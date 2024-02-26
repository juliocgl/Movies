package com.example.movies.controller;

import com.example.movies.exception.MovieNotFoundException;
import com.example.movies.model.Movie;
import com.example.movies.service.MovieService;
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

class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByTitle_ReturnsMovie_WhenTitleExists() throws MovieNotFoundException {
        String title = "Avatar";
        Movie expectedMovie = new Movie();
        when(movieService.get(title)).thenReturn(expectedMovie);

        ResponseEntity<Movie> response = movieController.getByTitle(title);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMovie, response.getBody());
        verify(movieService, times(1)).get(title);
    }

    @Test
    void getByTitle_ReturnsNotFound_WhenTitleDoesNotExist() throws MovieNotFoundException {
        String title = "NonExistentMovie";
        when(movieService.get(title)).thenThrow(new MovieNotFoundException("Movie not found"));

        ResponseEntity<Movie> response = movieController.getByTitle(title);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(movieService, times(1)).get(title);
    }

    @Test
    void isBestPictureWinner_ReturnsTrue_WhenTitleIsBestPictureWinner() throws MovieNotFoundException {
        String title = "BestPictureWinner";
        when(movieService.isBestPictureWinner(title)).thenReturn(true);

        ResponseEntity<Boolean> response = movieController.isBestPictureWinner(title);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(movieService, times(1)).isBestPictureWinner(title);
    }

    @Test
    void isBestPictureWinner_ReturnsFalse_WhenTitleIsNotBestPictureWinner() throws MovieNotFoundException {
        String title = "NonBestPictureWinner";
        when(movieService.isBestPictureWinner(title)).thenReturn(false);

        ResponseEntity<Boolean> response = movieController.isBestPictureWinner(title);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(movieService, times(1)).isBestPictureWinner(title);
    }

    @Test
    void getTopRatedMovies_ReturnsListOfMovies() {
        List<Movie> expectedMovies = Arrays.asList(new Movie(), new Movie());
        when(movieService.getTopRatedMovies()).thenReturn(expectedMovies);

        ResponseEntity<List<Movie>> response = movieController.getTopRatedMovies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMovies, response.getBody());
        verify(movieService, times(1)).getTopRatedMovies();
    }
}