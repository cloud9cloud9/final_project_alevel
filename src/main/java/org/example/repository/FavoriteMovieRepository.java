package org.example.repository;

import org.example.model.User;
import org.example.model.api_model.FavoriteMovie;
import org.example.model.api_model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {

    Optional<FavoriteMovie> findByMovieAndUser(Movie movie, User user);

    Optional<List<FavoriteMovie>> findAllByUser(User user);

    Optional<FavoriteMovie> findByUserAndAndMovie(User user, Movie movie);

}
