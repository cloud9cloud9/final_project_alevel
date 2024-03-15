package org.example.finalprojectalevel.service;

import org.example.dto.CommentDto;
import org.example.dto.CommentUpdateRequestDto;
import org.example.mapper.MovieMapper;
import org.example.model.User;
import org.example.model.api_model.Comment;
import org.example.model.api_model.Movie;
import org.example.repository.CommentRepository;
import org.example.service.CommentService;
import org.example.service.MovieService;
import org.example.service.impl.CommentServiceImpl;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentServiceTest {

    @InjectMocks
    @Autowired
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MovieService movieService;
    @Mock
    private MovieMapper movieMapper;

    @BeforeEach
    public void setUp() {
        commentRepository = Mockito.mock(CommentRepository.class);
        movieMapper = Mockito.mock(MovieMapper.class);
        movieService = Mockito.mock(MovieService.class);
        commentService = new CommentServiceImpl(commentRepository, movieService, movieMapper);
    }

    @Test
    public void testSaveComment_ValidInputs_ReturnsMovie() {
        // Arrange
        CommentDto comment = CommentDto.builder()
                .text("Test comment")
                .build();
        String imdbId = "tt1234567";
        User user = User.builder()
                .userName("testuser")
                .build();
        Movie movie = Movie.builder()
                .title("Test movie")
                .build();

        when(movieService.validateAndGetMovie(imdbId)).thenReturn(movie);

        Comment movieComment = Comment.builder()
                .text(comment.getText())
                .movie(movie)
                .authorId(1L)
                .build();
        when(movieMapper.toMovieComment(comment)).thenReturn(movieComment);

        // Mock the behavior of commentRepository.save(movieComment)
        when(commentRepository.save(movieComment)).thenReturn(movieComment);

        // Act
        Movie result = commentService.saveComment(comment, imdbId, user);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result);
    }


    @Test
    public void testDeleteCommentById_ValidId() {
        // Setup
        Long id = 1L;

        // Test
        commentService.deleteCommentById(id);

        // Verify
        verify(commentRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateExistingComment() {
        Long id = 1L;
        CommentUpdateRequestDto updateDto = CommentUpdateRequestDto.builder()
                .text("Updated text")
                .build();
        Comment existingComment = Comment.builder()
                .text("Original text")
                .timestamp(LocalDate.now())
                .build();
        existingComment.setId(id);

        when(commentRepository.findById(id)).thenReturn(Optional.of(existingComment));

        commentService.update(id, updateDto);

        assertEquals("Updated text", existingComment.getText());
        verify(commentRepository, times(1)).save(existingComment);
    }
}
