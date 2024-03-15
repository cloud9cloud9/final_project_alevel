package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.CommentDto;
import org.example.dto.CommentUpdateRequestDto;
import org.example.exception.CommentNotFoundException;
import org.example.mapper.MovieMapper;
import org.example.model.User;
import org.example.model.api_model.Comment;
import org.example.model.api_model.Movie;
import org.example.repository.CommentRepository;
import org.example.service.CommentService;
import org.example.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final MovieService movieService;

    private final MovieMapper movieMapper;

    @Override
    public Movie saveComment(@NonNull CommentDto comment,
                             @NonNull String imdbId,
                             @NonNull User user) {
        var movie = movieService.validateAndGetMovie(imdbId);
        var movieComment = movieMapper.toMovieComment(comment);
        movieComment.setMovie(movie);
        movieComment.setAuthorId(user.getId());
        movieComment.setTimestamp(LocalDate.now());
        commentRepository.save(movieComment);
        return movie;
    }

    @Override
    public void deleteCommentById(@NonNull Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void update(@NonNull Long id,
                       @NonNull CommentUpdateRequestDto comment) {
        var existingComment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
        existingComment.setText(comment.getText());
        commentRepository.save(existingComment);
    }

    @Override
    public ResponseEntity<?> handleCommentAuthorization(@NonNull Long commentId,
                                                        @NonNull User currentUser) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (comment == null || currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        if (currentUser.isAdmin()) {
            return null;
        } else if (currentUser.getId().equals(comment.getAuthorId())) {
            return null;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to update this comment");
        }
    }
}
