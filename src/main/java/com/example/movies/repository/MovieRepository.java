package com.example.movies.repository;

import com.example.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitleIgnoreCase(String title);

    @Query("SELECT m, COALESCE(AVG(r.rate), 0) as avgRating " +
            "FROM Movie m " +
            "INNER JOIN m.ratings r " +
            "GROUP BY m.id " +
            "ORDER BY avgRating DESC " +
            "LIMIT 10")
    List<Movie> findTop10RatedMoviesByAvgRating();
}
