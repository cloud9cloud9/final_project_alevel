package org.example.util;

import lombok.AllArgsConstructor;
import org.example.config.OmdbConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(value = OmdbConfigurationProperties.class)
public class GeneratorUrl {

    private final OmdbConfigurationProperties omdbConfigurationProperties;

    public String generateUrlWithPage(final String movieTitle, Integer page) {

        final var properties = omdbConfigurationProperties.getOmdb();
        return properties.getUrl().replace("{movie}", movieTitle).replace("{page}", String.valueOf(page))
                .replace("{key}", properties.getApiKey()).trim()
                .replace(" ", "_");

    }

    public String generateUrlWithoutPage(final String movieTitle) {

        final var properties = omdbConfigurationProperties.getOmdbsec();
        return properties.getUrl().replace("{key}", properties.getApiKey()).replace("{title}", movieTitle).trim()
                .replace(" ", "_");
    }

    public String generateUrlWithMovieId(final String imdbId) {

        final var properties = omdbConfigurationProperties.getOmdbthird();
        return properties.getUrl().replace("{key}", properties.getApiKey()).replace("{imdbid}", imdbId).trim()
                .replace(" ", "_");
    }
}
