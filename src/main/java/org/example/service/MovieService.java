package org.example.service;

import org.example.dto.MovieDto;
import org.example.dto.UpdateMovieRequestDto;
import org.example.model.api_model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {

    Movie validateAndGetMovie(String imdbId);

    ResponseEntity<MovieDto> findById(String imdbId);

    ResponseEntity<MovieDto> find(String movieTitle);

    ResponseEntity<List<MovieDto>> findMovies(String movieTitle, Integer page);


    void update(String imdbId,
                UpdateMovieRequestDto movie);

    void deleteByImdbId(String imdbId);

}
