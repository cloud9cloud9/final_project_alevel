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

import java.util.List;

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
        Movie movie = movieService.validateAndGetMovie(imdbId);
        Comment movieComment = movieMapper.toMovieComment(comment);
        movieComment.setMovie(movie);
        movieComment.setAuthorId(user.getId());
        commentRepository.save(movieComment);
        return movie;
    }

    @Override
    public void deleteCommentById(@NonNull Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findAllByMovieImdbId(@NonNull String imdbId) {
        return commentRepository.findAllByMovie_ImdbId(imdbId);
    }

    @Override
    public void update(@NonNull Long id,
                       @NonNull CommentUpdateRequestDto comment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
        existingComment.setText(comment.getText());
        commentRepository.save(existingComment);
    }

    @Override
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public ResponseEntity<?> handleCommentAuthorization(Long commentId, User currentUser) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if (comment == null || currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        if (!comment.getAuthorId().equals(currentUser.getId()) && !currentUser.isAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to update this comment");
        }
        return null;
    }
}
