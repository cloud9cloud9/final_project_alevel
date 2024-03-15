package org.example.finalprojectalevel.repository;

import org.example.exception.MovieNotFoundException;
import org.example.model.api_model.Movie;
import org.example.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    public void testSaveAndFindById() {
        // Arrange
        Movie movie = Movie.builder()
                .imdbId("tt1234569012")
                .title("some")
                .year("2023")
                .type("movie")
                .poster("http://example.com/poster.jpgssss")
                .build();

        // Act
        movieRepository.save(movie);
        Movie foundMovie = movieRepository.findById(movie.getImdbId()).orElse(null);

        // Assert
        assertNotNull(foundMovie);
        assertEquals(movie.getImdbId(), foundMovie.getImdbId());
        assertEquals(movie.getTitle(), foundMovie.getTitle());
        assertEquals(movie.getYear(), foundMovie.getYear());
        assertEquals(movie.getType(), foundMovie.getType());
        assertEquals(movie.getPoster(), foundMovie.getPoster());
    }

    @Test
    public void testFindMovieByTitle() {
        // Arrange
        String movieTitle = "Test Movie";
        Movie movie = Movie.builder()
                .imdbId("tt123456712313")
                .title(movieTitle)
                .year("2023")
                .type("episode")
                .poster("http://example.com/poster.jpgadasdadwda")
                .build();

        // Act
        movieRepository.save(movie);
        Movie foundMovie = movieRepository.findMovieByTitle(movieTitle).orElseThrow(MovieNotFoundException::new);

        // Assert
        assertNotNull(foundMovie);
        assertEquals(movieTitle, foundMovie.getTitle());
    }

    @Test
    public void testSaveAll() {
        // Arrange
        List<Movie> movieEntities = List.of(
                Movie.builder().imdbId("tt1111111").title("Movie 1").year("2021").type("movie").poster("poster1.jpg1").build(),
                Movie.builder().imdbId("tt2222222").title("Movie 2").year("2022").type("episode").poster("poster2.jpg2").build(),
                Movie.builder().imdbId("tt3333333").title("Movie 3").year("2023").type("movie").poster("poster3.jpg3").build()
        );

        long countBefore = movieRepository.count();
        // Act
        movieRepository.saveAll(movieEntities);
        long countAfter = movieRepository.count();

        // Assert
        assertEquals(3,countAfter - countBefore);
    }
    @Test
    public void testDeleteByImdbId() {
        // Arrange
        Movie movie = Movie.builder()
                .imdbId("tt11111")
                .title("Movie")
                .year("202")
                .type("movie")
                .poster("poster1.jpg11")
                .build();
        var maybeMovie = movieRepository.save(movie);
        Optional<Movie> movieByTitle = movieRepository.findMovieByTitle(maybeMovie.getTitle());
        assertTrue(movieByTitle.isPresent());
        movieByTitle.ifPresent(movieRepository::delete);
        assertTrue(movieRepository.findById(maybeMovie.getImdbId()).isEmpty());

    }
}
