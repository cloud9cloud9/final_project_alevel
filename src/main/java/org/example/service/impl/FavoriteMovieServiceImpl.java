package org.example.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.exception.EmptyFavoriteMoviesException;
import org.example.exception.FavoriteMovieNotFoundException;
import org.example.model.User;
import org.example.model.api_model.FavoriteMovie;
import org.example.model.api_model.Movie;
import org.example.repository.FavoriteMovieRepository;
import org.example.service.FavoriteMovieService;
import org.example.service.MovieService;
import org.example.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteMovieServiceImpl implements FavoriteMovieService {

    private final FavoriteMovieRepository favoriteMovieRepository;

    private final MovieService movieService;

    private final UserService userService;

    @Override
    public void addMovie(Long userId, String movieId) {
        FavoriteMovie favoriteMovie = FavoriteMovie.builder()
                .movie(movieService.validateAndGetMovie(movieId))
                .user(userService.findById(userId))
                .build();
        favoriteMovieRepository.save(favoriteMovie);
    }

    @Override
    public void removeMovie(Long userId, String movieId) {
        FavoriteMovie favoriteMovie = favoriteMovieRepository.findByMovieAndUser(movieService.validateAndGetMovie(movieId),
                        userService.findById(userId))
                .orElseThrow(FavoriteMovieNotFoundException::new);
        favoriteMovieRepository.delete(favoriteMovie);
    }

    @Override
    public List<FavoriteMovie> findAllByUser(User user) {
        return favoriteMovieRepository.findAllByUser(user)
                .orElseThrow(EmptyFavoriteMoviesException::new);
    }

    @Override
    public FavoriteMovie findByUserAndAndMovie(User user, Movie movie) {
        List<FavoriteMovie> movies = favoriteMovieRepository.findAllByUser(user)
                .orElseThrow(EmptyFavoriteMoviesException::new);
        Optional<FavoriteMovie> favoriteMovie = movies.stream()
                .filter(favoriteMovies -> favoriteMovies.getMovie().equals(movie))
                .findFirst();
        return favoriteMovie.orElseThrow(FavoriteMovieNotFoundException::new);
    }
}
