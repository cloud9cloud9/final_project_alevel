package org.example.finalprojectalevel.service;


import lombok.RequiredArgsConstructor;
import org.example.client.OmdbClient;
import org.example.dto.MovieDto;
import org.example.exception.MovieNotFoundException;
import org.example.mapper.MovieMapper;
import org.example.model.api_model.Movie;
import org.example.repository.MovieRepository;
import org.example.service.MovieService;
import org.example.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RequiredArgsConstructor
public class MovieServiceTest {


    @InjectMocks
    @Autowired
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private OmdbClient omdbClient;

    @Mock
    private MovieMapper movieMapper;

    @BeforeEach
    public void setUp() {
        omdbClient = Mockito.mock(OmdbClient.class);
        movieMapper = Mockito.mock(MovieMapper.class);
        movieRepository = Mockito.mock(MovieRepository.class);
        movieService = new MovieServiceImpl(omdbClient, movieRepository, movieMapper);
    }

    @Test
    public void testValidateAndGetMovie_ValidId_ReturnsMovie() {
        // Setup
        String validImdbId = "tt123456";
        Movie validMovie = Movie.builder()
                .title("Movie Title")
                .imdbId(validImdbId)
                .type("movie")
                .build();

        when(movieRepository.findById(validImdbId)).thenReturn(Optional.of(validMovie));

        // Execute
        Movie result = movieService.validateAndGetMovie(validImdbId);

        // Verify
        assertNotNull(result);
        assertEquals(validImdbId, result.getImdbId());
    }

    @Test
    void validateAndGetMovie_WhenMovieDoesNotExist_ShouldThrowException() {
        // Arrange
        String imdbId = "tt1234567";
        when(movieRepository.findById(imdbId)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(MovieNotFoundException.class, () -> movieService.validateAndGetMovie(imdbId));
    }

    @Test
    public void testFindByIdWhenMovieDtoIsNotNull() {
        String imdbId = "tt123456";
        MovieDto movieDto = MovieDto.builder()
                .year("2022")
                .title("Movie Title")
                .imdbId(imdbId)
                .build();
        when(omdbClient.findMovieById(imdbId)).thenReturn(movieDto);

        ResponseEntity<MovieDto> response = movieService.findById(imdbId);

        assertNotNull(response.getBody());
        assertEquals(movieDto, response.getBody());
    }

    @Test
    void testFindMovies_ValidMovieTitle_ValidPage() {
        // Arrange
        String movieTitle = "Inception";
        Integer page = 1;
        // Define the response from the third-party API
        Map<String, Object> omdbResponse = new HashMap<>();
        List<Map<String, String>> searchResults = new ArrayList<>();
        Map<String, String> movieData = new HashMap<>();
        movieData.put("Title", "Inception");
        movieData.put("Year", "2010");
        movieData.put("imdbID", "tt1375666");
        movieData.put("Type", "movie");
        movieData.put("Poster", "http://someposterurl.com");
        searchResults.add(movieData);
        omdbResponse.put("Search", searchResults);
        when(omdbClient.searchMovies(movieTitle, page)).thenReturn(omdbResponse);

        // Act
        ResponseEntity<List<MovieDto>> response = movieService.findMovies(movieTitle, page);

        // Assert
        assertEquals(1, response.getBody().size());
        assertEquals("Inception", response.getBody().get(0).getTitle());
        assertEquals("2010", response.getBody().get(0).getYear());
        assertEquals("tt1375666", response.getBody().get(0).getImdbId());
        assertEquals("movie", response.getBody().get(0).getType());
        assertEquals("http://someposterurl.com", response.getBody().get(0).getPoster());
    }

    @Test
    public void testDeleteByImdbId_ValidImdbId_DeletesMovie() {
        // Given
        String imdbId = "tt1234567";
        // When
        movieService.deleteByImdbId(imdbId);
        // Then
        Mockito.verify(movieRepository).deleteByImdbId(imdbId);
    }
}
