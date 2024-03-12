package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MovieDto;
import org.example.model.User;
import org.example.model.api_model.FavoriteMovie;
import org.example.model.api_model.Movie;
import org.example.service.FavoriteMovieService;
import org.example.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/favorite")
public class FavoriteMovieController {

    private final FavoriteMovieService favoriteMovieService;

    private final MovieService movieService;


    @GetMapping
    public ResponseEntity<?> getAllFavoriteMovies(Authentication authentication) {
        log.info("Get all favorite movies");
        User currentUser = authentication.getPrincipal() instanceof User ?
                (User) authentication.getPrincipal() : null;
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        List<FavoriteMovie> movies = favoriteMovieService.findAllByUser(currentUser);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<?> getFavoriteMovie(@PathVariable("movieId") String movieId,
                                              Authentication authentication) {
        log.info("Get favorite movie");
        User currentUser = authentication.getPrincipal() instanceof User ?
                (User) authentication.getPrincipal() : null;
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        Movie movie = movieService.validateAndGetMovie(movieId);
        FavoriteMovie favMovie = favoriteMovieService.findByUserAndAndMovie(currentUser, movie);
        return ResponseEntity.ok(favMovie);
    }


    @PostMapping("/{movieId}")
    public ResponseEntity<?> addFavoriteMovie(@PathVariable("movieId") String movieId,
                                              Authentication authentication) {
        log.info("Add favorite movie");
        User currentUser = authentication.getPrincipal() instanceof User ?
                (User) authentication.getPrincipal() : null;
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        favoriteMovieService.addMovie(currentUser.getId(), movieId);
        return ResponseEntity.ok("Favorite movie added successfully");
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> deleteFavoriteMovie(@PathVariable("movieId") String movieId,
                                                 Authentication authentication) {
        log.info("Delete favorite movie");
        User currentUser = authentication.getPrincipal() instanceof User ?
                (User) authentication.getPrincipal() : null;
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        favoriteMovieService.removeMovie(currentUser.getId(), movieId);
        return ResponseEntity.ok("Favorite movie deleted successfully");
    }
}
