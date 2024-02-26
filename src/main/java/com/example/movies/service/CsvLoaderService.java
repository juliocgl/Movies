package com.example.movies.service;

import com.example.movies.model.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Service to load the CSV with the Oscar Best Pictures in the database
 */
@Service
public class CsvLoaderService {

    private static final Integer CATEGORY = 1;
    private static final Integer NOMINEE = 2;
    private static final Integer IS_WINNER = 4;
    private static final String MOVIE_WINNER_TAG = "Best Picture";
    private static final String IS_WINNER_TAG = "YES";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void loadCsvData() {
        try (InputStream inputStream = getClass().getResourceAsStream("/academy_awards.csv")) {
            assert inputStream != null;
            try (InputStreamReader reader = new InputStreamReader(inputStream);
                 CSVParser csvParser = CSVFormat.DEFAULT.parse(reader)) {

                for (CSVRecord record : csvParser) {
                    if (MOVIE_WINNER_TAG.equals(record.get(CATEGORY))) {
                        Movie movie = new Movie();
                        movie.setTitle(record.get(NOMINEE));
                        movie.setBestPictureWinner(IS_WINNER_TAG.equals(record.get(IS_WINNER)));
                        entityManager.persist(movie);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading academy awards CSV", e);
        }
    }
}
