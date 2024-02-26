package com.example.movies.configuration;

import com.example.movies.service.CsvLoaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    public CommandLineRunner initDatabase(CsvLoaderService csvLoaderService) {
        return args -> csvLoaderService.loadCsvData();
    }
}
