package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.ApiConstantPath;
import org.example.dto.MovieDto;
import org.example.dto.UpdateMovieRequestDto;
import org.example.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(ApiConstantPath.API_V1_MOVIE)
public class MovieController {

    private final MovieService movieService;

    @GetMapping(ApiConstantPath.RESULTS)
    @Operation(summary = "Find movies",
            description = "Method provided to find movies, keyword and page aren't optional")
    public ResponseEntity<List<MovieDto>> findMovies(@RequestParam("page") Integer page,
                                                     @RequestParam("movieTitle") String movieTitle) {
        log.info("Find movies with title: {}", movieTitle);
        return movieService.findMovies(movieTitle, page);
    }


    @GetMapping(ApiConstantPath.RESULT)
    @Operation(summary = "Find movie",
            description = "Method provided to find movie, via its title, value is required")
    public ResponseEntity<MovieDto> findMovie(@RequestParam("movieTitle") String movieTitle) {
        log.info("Find movie with title: {}", movieTitle);
        return movieService.find(movieTitle);
    }

    @PutMapping("/{imdbId}")
    @Operation(summary = "Update movie",
            description = "Method provided to update movie, via its title, value is required")
    public void updateMovie(@PathVariable("imdbId") String imdbId,
                                         @RequestBody UpdateMovieRequestDto updateMovieRequestDto) {
        log.info("Update movie with id: {}", imdbId);
        movieService.update(imdbId, updateMovieRequestDto);
    }

    @DeleteMapping("/{imdbId}")
    @Operation(summary = "Delete movie",
            description = "Method provided to delete movie, via its title, value is required")
    public void deleteMovie(@PathVariable("imdbId") String imdbId) {
        log.info("Delete movie with id: {}", imdbId);
        movieService.deleteByImdbId(imdbId);
    }
}
