package org.example.service;

import org.example.dto.CommentDto;
import org.example.dto.CommentUpdateRequestDto;
import org.example.model.User;
import org.example.model.api_model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    Movie saveComment(CommentDto comment,
                      String imdbId,
                      User user);

    void deleteCommentById(Long id);

    void update(Long id, CommentUpdateRequestDto comment);

    ResponseEntity<?> handleCommentAuthorization(Long commentId, User currentUser);

}
