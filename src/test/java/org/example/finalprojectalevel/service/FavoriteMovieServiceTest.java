package org.example.finalprojectalevel.service;

import org.example.model.User;
import org.example.model.api_model.FavoriteMovie;
import org.example.model.api_model.Movie;
import org.example.repository.FavoriteMovieRepository;
import org.example.service.FavoriteMovieService;
import org.example.service.MovieService;
import org.example.service.UserService;
import org.example.service.impl.FavoriteMovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FavoriteMovieServiceTest {


    @Mock
    private MovieService movieService;

    @Mock
    private UserService userService;

    @Mock
    private FavoriteMovieRepository favoriteMovieRepository;

    @InjectMocks
    @Autowired
    private FavoriteMovieService favoriteMovieService;

    @BeforeEach
    public void setUp() {

        favoriteMovieRepository = Mockito.mock(FavoriteMovieRepository.class);
        movieService = Mockito.mock(MovieService.class);
        userService = Mockito.mock(UserService.class);
        favoriteMovieService = new FavoriteMovieServiceImpl(favoriteMovieRepository, movieService, userService);
    }

    @Test
    public void testAddMovie_ValidIds_Success() {
        Long userId = 1L;
        String movieId = "123";

        Movie movie = Movie.builder()
                .imdbId(movieId)
                .title("Test")
                .year("2020")
                .build();

        User user = User.builder()
                .password("123")
                .email("5QpP1@example.com")
                .userName("testuser")
                .build();
        when(movieService.validateAndGetMovie(movieId)).thenReturn(movie);
        when(userService.findById(userId)).thenReturn(user);

        favoriteMovieService.addMovie(userId, movieId);

        verify(favoriteMovieRepository, times(1))
                .save(any(FavoriteMovie.class));
    }

    @Test
    public void testRemoveMovie_movieExistsInFavorites() {
        Long userId = 1L;
        String movieId = "123";
        Movie movie = new Movie(); // Create a movie object for testing
        User user = new User(); // Create a user object for testing
        FavoriteMovie favoriteMovie = new FavoriteMovie(); // Create a favoriteMovie object for testing

        when(movieService.validateAndGetMovie(movieId)).thenReturn(movie);
        when(userService.findById(userId)).thenReturn(user);
        when(favoriteMovieRepository.findByMovieAndUser(movie, user)).thenReturn(java.util.Optional.of(favoriteMovie));

        favoriteMovieService.removeMovie(userId, movieId);

        verify(favoriteMovieRepository, times(1)).delete(favoriteMovie);
    }

    @Test
    public void testFindAllByUser_WhenFavoriteMoviesFound() {
        User user = new User();
        List<FavoriteMovie> expectedMovies = Collections.singletonList(new FavoriteMovie());

        when(favoriteMovieRepository.findAllByUser(user)).thenReturn(Optional.of(expectedMovies));

        List<FavoriteMovie> actualMovies = favoriteMovieService.findAllByUser(user);

        assertEquals(expectedMovies, actualMovies);
    }
}
