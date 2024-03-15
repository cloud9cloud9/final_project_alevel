package org.example.client;

import lombok.RequiredArgsConstructor;
import org.example.config.OmdbConfigurationProperties;
import org.example.config.OmdbConfigurationProperties.*;
import org.example.dto.MovieDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OmdbClient {

    private final RestTemplate restTemplate;

    private final OmdbConfigurationProperties omdbConfigurationProperties;

    private final String searchMovies = "/?s={movieTitle}&page={page}&apikey={key}";

    private final String searchMoviesByTitle = "/?apikey={key}&t={movieTitle}";

    private final String searchMovieByImdbId = "/?apikey={key}&i={imdbId}";

    public Map<String, Object> searchMovies(String title, Integer page) {
        final OMDB omdb = omdbConfigurationProperties.getOmdb();
        final String movieTitle = title.replace(" ", "_");

        final String url = new UriTemplate(omdb.getUrl() + searchMovies)
                .expand(movieTitle, page, omdb.getApiKey())
                .toString();
        return restTemplate.getForObject(url, Map.class);
    }

    public MovieDto findMovieById(String imdbId) {
        final OMDB omdb = omdbConfigurationProperties.getOmdb();

        final String url = new UriTemplate(omdb.getUrl() + searchMovieByImdbId)
                .expand(omdb.getApiKey(), imdbId)
                .toString();
        return restTemplate.getForObject(url, MovieDto.class);
    }

    public MovieDto findMovieByTitle(String title) {
        final OMDB omdb = omdbConfigurationProperties.getOmdb();
        final String movieTitle = title.replace(" ", "_");

        final String url = new UriTemplate(omdb.getUrl() + searchMoviesByTitle)
                .expand(omdb.getApiKey(), movieTitle)
                .toString();
        return restTemplate.getForObject(url, MovieDto.class);
    }
}
