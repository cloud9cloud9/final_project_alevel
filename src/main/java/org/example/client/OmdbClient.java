package org.example.client;

import lombok.RequiredArgsConstructor;
import org.example.config.OmdbConfigurationProperties;
import org.example.dto.MovieDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OmdbClient {

    private final RestTemplate restTemplate;

    private final OmdbConfigurationProperties omdbConfigurationProperties;

    private final String fPattern = "/?s={movieTitle}&page={page}&apikey={key}";

    private final String sPattern = "/?apikey={key}&t={movieTitle}";

    private final String tPattern = "/?apikey={key}&i={imdbId}";

    @GetMapping
    public Map searchMovies(String title, Integer page) {
        final var omdb = omdbConfigurationProperties.getOmdb();
        final var movieTitle = title.replace(" ", "_");

        final var url = new UriTemplate(omdb.getUrl() + fPattern)
                .expand(movieTitle, page, omdb.getApiKey())
                .toString();
        return restTemplate.getForObject(url, Map.class);
    }

    @GetMapping
    public MovieDto findMovieById(String imdbId) {
        final var omdb = omdbConfigurationProperties.getOmdb();

        final var url = new UriTemplate(omdb.getUrl() + tPattern)
                .expand(omdb.getApiKey(), imdbId)
                .toString();
        return restTemplate.getForObject(url, MovieDto.class);
    }

    @GetMapping
    public MovieDto findMovieByTitle(String title) {
        final var omdb = omdbConfigurationProperties.getOmdb();
        final var movieTitle = title.replace(" ", "_");

        final var url = new UriTemplate(omdb.getUrl() + sPattern)
                .expand(omdb.getApiKey(), movieTitle)
                .toString();
        return restTemplate.getForObject(url, MovieDto.class);
    }
}
