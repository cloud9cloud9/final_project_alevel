package org.example.finalprojectalevel.repository;

import org.example.model.User;
import org.example.model.api_model.Comment;
import org.example.model.api_model.Movie;
import org.example.repository.CommentRepository;
import org.example.repository.MovieRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Test
    public void testSave() {
        // Arrange
        User user = User.builder()
                .userName("test")
                .password("test")
                .email("test")
                .build();
        userRepository.save(user);

        Movie movie = Movie.builder()
                .imdbId("tt123456789")
                .title("test")
                .year("2023")
                .type("movie")
                .poster("http://example.com/poster.jpg")
                .build();

        movieRepository.save(movie);

        Comment comment = Comment.builder()
                .authorId(user.getId())
                .movie(movie)
                .text("test")
                .timestamp(LocalDate.now())
                .build();
        commentRepository.save(comment);
        assertNotNull(comment.getId());
        assertEquals(comment.getAuthorId(), user.getId());
        assertEquals(comment.getMovie().getImdbId(), movie.getImdbId());
    }

    @Test
    public void testFindById() {
        // Arrange
        User user = User.builder()
                .userName("test1")
                .password("test1")
                .email("test1")
                .build();
        userRepository.save(user);

        Movie movie = Movie.builder()
                .imdbId("tt1234567891")
                .title("test1")
                .year("20231")
                .type("movie1")
                .poster("http://example.com/poster.jpggg")
                .build();

        movieRepository.save(movie);

        Comment comment = Comment.builder()
                .authorId(user.getId())
                .movie(movie)
                .text("test")
                .timestamp(LocalDate.now())
                .build();
        commentRepository.save(comment);

        // Act
        Optional<Comment> foundComment = commentRepository.findById(comment.getId());

        // Assert
        assertTrue(foundComment.isPresent());
        assertEquals(comment.getId(), foundComment.get().getId());
        assertEquals(comment.getAuthorId(), foundComment.get().getAuthorId());
        assertEquals(comment.getMovie().getImdbId(), foundComment.get().getMovie().getImdbId());
        assertEquals(comment.getText(), foundComment.get().getText());
        assertEquals(comment.getTimestamp(), foundComment.get().getTimestamp());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        User user = User.builder()
                .userName("test2")
                .password("test2")
                .email("test2")
                .build();
        userRepository.save(user);

        Movie movie = Movie.builder()
                .imdbId("tt12345678922")
                .title("test2")
                .year("20232")
                .type("movie2")
                .poster("http://example.com/poster.jpg22")
                .build();

        movieRepository.save(movie);

        Comment comment = Comment.builder()
                .authorId(user.getId())
                .movie(movie)
                .text("test")
                .timestamp(LocalDate.now())
                .build();
        commentRepository.save(comment);

        // Act
        commentRepository.deleteById(comment.getId());

        // Assert
        assertFalse(commentRepository.findById(comment.getId()).isPresent());
    }
}
