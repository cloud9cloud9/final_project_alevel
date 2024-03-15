package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.client.OmdbClient;
import org.example.dto.MovieDto;
import org.example.dto.UpdateMovieRequestDto;
import org.example.exception.MovieNotFoundException;
import org.example.mapper.MovieMapper;
import org.example.model.api_model.Movie;
import org.example.repository.MovieRepository;
import org.example.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private final OmdbClient omdbClient;

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    @Override
    public Movie validateAndGetMovie(@NonNull final String imdbId) {
        var movie = movieRepository.findById(imdbId)
                .orElseThrow(MovieNotFoundException::new);
        if(movie == null) {
            findById(imdbId);
        }
        return movie;
    }

    @Override
    public ResponseEntity<MovieDto> findById(@NonNull final String imdbId) {
        final var movieOptional = movieRepository.findById(imdbId);

        if (movieOptional.isPresent()) {
            var movie = movieOptional.get();
            var movieDto = movieMapper.toMovieDto(movie);
            return ResponseEntity.ok(movieDto);

        } else {
            final var movieDto = omdbClient.findMovieById(imdbId);

            if (movieDto != null) {
                var movie = movieMapper.toMovie(movieDto);
                movieRepository.save(movie);
                return ResponseEntity.ok(movieDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @Override
    public ResponseEntity<MovieDto> find(@NonNull final String movieTitle) {
        final var movieOptional = movieRepository.findMovieByTitle(movieTitle);

        if (movieOptional.isPresent()) {
            var movie = movieOptional.get();
            var movieDto = movieMapper.toMovieDto(movie);
            return ResponseEntity.ok(movieDto);

        } else {
            final var movieDto = omdbClient.findMovieByTitle(movieTitle);

            if (movieDto != null) {
                var movie = movieMapper.toMovie(movieDto);
                movieRepository.save(movie);
                return ResponseEntity.ok(movieDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @Override
    public ResponseEntity<List<MovieDto>> findMovies(@NonNull final String movieTitle,
                                                     @NonNull Integer page) {
        final var movieDto = omdbClient.searchMovies(movieTitle, page);
        List<Map<String, String>> searchResults = (List<Map<String, String>>) movieDto.get("Search");
        List<MovieDto> movies = searchResults.stream()
                .map(result -> MovieDto.builder()
                        .title(result.get("Title"))
                        .year(result.get("Year"))
                        .imdbId(result.get("imdbID"))
                        .type(result.get("Type"))
                        .poster(result.get("Poster"))
                        .build())
                .collect(Collectors.toList());

        if(!movies.isEmpty()) {
            List<Movie> movieEntities = movieMapper.toMovie(movies);
            movieEntities.forEach(movie -> movie.getComments().forEach(comment -> comment.setMovie(movie)));
            movieRepository.saveAll(movieEntities);
        }

        return ResponseEntity.ok(movies);
    }


    @Override
    public void update(@NonNull final String imdbId,
                       @NonNull UpdateMovieRequestDto updatedMovie) {
        var movie = validateAndGetMovie(imdbId);
        movie.setYear(updatedMovie.getYear());
        movie.setTitle(updatedMovie.getTitle());
        movie.setType(updatedMovie.getType());
        movie.setPoster(updatedMovie.getPoster());
        movieRepository.save(movie);
    }

    @Override
    public void deleteByImdbId(@NonNull final String imdbId) {
        movieRepository.deleteByImdbId(imdbId);
    }
}
