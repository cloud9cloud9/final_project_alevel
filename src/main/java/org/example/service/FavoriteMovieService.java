package org.example.service;

import org.example.model.User;
import org.example.model.api_model.FavoriteMovie;
import org.example.model.api_model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FavoriteMovieService {

    void addMovie(Long userId, String movieId);

    void removeMovie(Long userId, String movieId);

    List<FavoriteMovie> findAllByUser(User user);

    FavoriteMovie findByUserAndAndMovie(User user, Movie movie);

}
