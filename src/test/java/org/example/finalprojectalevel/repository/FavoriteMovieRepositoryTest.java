package org.example.finalprojectalevel.repository;


import org.example.model.User;
import org.example.model.api_model.FavoriteMovie;
import org.example.model.api_model.Movie;
import org.example.repository.FavoriteMovieRepository;
import org.example.repository.MovieRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FavoriteMovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteMovieRepository favoriteMovieRepository;

    @Test
    public void testCreateFavoriteMovie() {
        // Arrange
        User user = userRepository.save(User.builder()
                .userName("testssss")
                .password("testssss")
                .email("testssss")
                .build());


        Movie movie = movieRepository.save(Movie.builder()
                .imdbId("tt123456789")
                .title("test")
                .year("2023")
                .type("movie")
                .poster("http://example.com/poster.jpg")
                .build());

        FavoriteMovie favoriteMovie = FavoriteMovie.builder()
                .user(user)
                .movie(movie)
                .build();

        // Act
        FavoriteMovie savedFavoriteMovie = favoriteMovieRepository.save(favoriteMovie);

        // Assert
        assertNotNull(savedFavoriteMovie.getId());
        assertEquals(user.getId(), savedFavoriteMovie.getUser().getId());
        assertEquals(movie.getImdbId(), savedFavoriteMovie.getMovie().getImdbId());
    }

    @Test
    public void testFindByMovieAndUser() {

        User user = userRepository.save(User.builder()
                .userName("test1")
                .password("test1")
                .email("test1")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .imdbId("tt123456789")
                .title("test1")
                .year("2023")
                .type("movie")
                .poster("http://example.com/poster.jpg")
                .build());

        favoriteMovieRepository.save(FavoriteMovie.builder()
                .movie(movie)
                .user(user)
                .build());

        Optional<FavoriteMovie> foundFavoriteMovie = favoriteMovieRepository.findByMovieAndUser(movie, user);

        assertTrue(foundFavoriteMovie.isPresent());
        assertEquals(user.getId(), foundFavoriteMovie.get().getUser().getId());
        assertEquals(movie.getImdbId(), foundFavoriteMovie.get().getMovie().getImdbId());
    }

    @Test
    public void testDelete() {
        // Arrange
        User user = userRepository.save(User.builder()
                .userName("test111")
                .password("test111")
                .email("test111")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .imdbId("tt12345678911")
                .title("test111")
                .year("202311")
                .type("movie11")
                .poster("http://example.com/poster.jpg11")
                .build());

        FavoriteMovie favoriteMovie = FavoriteMovie.builder()
                .user(user)
                .movie(movie)
                .build();

        // Act
        favoriteMovieRepository.delete(favoriteMovie);

        // Assert
        assertFalse(favoriteMovieRepository.findByMovieAndUser(movie, user).isPresent());
    }
}
