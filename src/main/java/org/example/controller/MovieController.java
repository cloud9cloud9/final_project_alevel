package org.example.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.Template;
import org.example.dto.MovieDto;
import org.example.dto.MovieResponse;
import org.example.dto.UpdateMovieRequestDto;
import org.example.mapper.MovieMapper;
import org.example.model.api_model.Movie;
import org.example.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.constant.ApiConstantPath.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API_V1_MOVIE)
public class MovieController {

    private final MovieService movieService;

    @GetMapping(RESULTS)
    public ResponseEntity<List<MovieDto>> findMovies(@RequestParam("page") Integer page,
                                                     @RequestParam("movieTitle") String movieTitle) {
        log.info("Find movies with title: {}", movieTitle);
        return movieService.findMovies(movieTitle, page);
    }


    @GetMapping(RESULT)
    public ResponseEntity<MovieDto> findMovie(@RequestParam("movieTitle") String movieTitle) {
        log.info("Find movie with title: {}", movieTitle);
        return movieService.find(movieTitle);
    }

    @PutMapping("/{imdbId}")
    public ResponseEntity<?> updateMovie(@PathVariable("imdbId") String imdbId,
                                         @RequestBody UpdateMovieRequestDto updateMovieRequestDto) {
        log.info("Update movie with id: {}", imdbId);
        movieService.update(imdbId, updateMovieRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{imdbId}")
    public ResponseEntity<?> deleteMovie(@PathVariable("imdbId") String imdbId) {
        log.info("Delete movie with id: {}", imdbId);
        movieService.deleteByImdbId(imdbId);
        return ResponseEntity.ok().build();
    }
}
