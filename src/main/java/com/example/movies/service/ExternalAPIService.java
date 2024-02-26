package com.example.movies.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

/**
 * Service responsible for calling the external API "OMDbAPI".
 */
@Service
public class ExternalAPIService {

    private static final String OMDB_API_BASE_URL = "http://www.omdbapi.com";
    @Value("${omdb.api.key}")
    private String OMDB_API_KEY;
    private final WebClient.Builder webClientBuilder;

    public ExternalAPIService() {
        this.webClientBuilder = WebClient.builder();
    }

    public Long getMovieBoxOfficeFromExternalAPI(String title) {
        Optional<String> response = webClientBuilder.build()
                .get()
                .uri(OMDB_API_BASE_URL + "/?t={title}&apikey={apikey}", title, OMDB_API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .blockOptional();

        ObjectMapper objectMapper = new ObjectMapper();
        if (response.isPresent()) {
            try {
                JsonNode jsonNode = objectMapper.readTree(response.get());
                String boxOfficeValue = jsonNode.get("BoxOffice").asText().replace("$", "").replace(",", "");
                return Long.parseLong(boxOfficeValue);
            } catch (JsonProcessingException | NumberFormatException ignored) {
            }
        }
        return -1L;
    }
}
