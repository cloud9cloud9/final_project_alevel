package org.example.repository;

import org.example.model.api_model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    void deleteByImdbId(String imdbId);

    Optional<Movie> findMovieByTitle(String title);

}
