package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.MovieDto;
import org.example.dto.UpdateMovieRequestDto;
import org.example.exception.MovieNotFoundException;
import org.example.mapper.MovieMapper;
import org.example.model.api_model.Movie;
import org.example.repository.MovieRepository;
import org.example.service.MovieService;
import org.example.util.GeneratorUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private final GeneratorUrl generatorUrl;

    private final RestTemplate restTemplate;

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    @Override
    public Movie validateAndGetMovie(String imdbId) {
        Movie movie = movieRepository.findById(imdbId)
                .orElseThrow(MovieNotFoundException::new);
        if(movie == null) {
            findById(imdbId);
        }
        return movie;
    }

    @Override
    public ResponseEntity<MovieDto> findById(String imdbId) {
        final var response = restTemplate.getForEntity(generatorUrl.generateUrlWithMovieId(imdbId), MovieDto.class);
        final var movieDto = response.getBody();

        if(movieDto != null) {
            Movie movie = movieMapper.toMovie(movieDto);
            movieRepository.save(movie);
        }
        return ResponseEntity.ok(movieDto);
    }

    @Override
    public ResponseEntity<MovieDto> find(@NonNull final String movieTitle) {
        final var response = restTemplate.getForEntity(generatorUrl.generateUrlWithoutPage(movieTitle), MovieDto.class);
        final var movieDto = response.getBody();

        if(movieDto != null) {
            Movie movieByTitle = movieRepository.findMovieByTitle(movieDto.getTitle());
            return ResponseEntity.ok(movieMapper.toMovieDto(movieByTitle));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<MovieDto>> findMovies(final String movieTitle,
                                                     @NonNull Integer page) {
        final var response = restTemplate.getForObject(generatorUrl.generateUrlWithPage(movieTitle, page), Map.class);
        List<Map<String, String>> searchResults = (List<Map<String, String>>) response.get("Search");
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
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void saveAllMovie(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

    @Override
    public void deleteMovie(Movie movie) {
        Movie validMovie = validateAndGetMovie(movie.getImdbId());
        movieRepository.delete(validMovie);
    }

    @Override
    public void update(String imdbId,
                       UpdateMovieRequestDto updatedMovie) {
        Movie movie = validateAndGetMovie(imdbId);
        movie.setYear(updatedMovie.year());
        movie.setTitle(updatedMovie.title());
        movie.setType(updatedMovie.type());
        movie.setPoster(updatedMovie.poster());
        movieRepository.save(movie);
    }

    @Override
    public void deleteByImdbId(String imdbId) {
        movieRepository.deleteByImdbId(imdbId);
    }
}
