package org.example.repository;

import org.example.model.api_model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    void deleteByImdbId(String imdbId);

    Movie findMovieByTitle(String title);

}
