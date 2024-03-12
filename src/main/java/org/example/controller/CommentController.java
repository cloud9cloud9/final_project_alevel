package org.example.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.CommentDto;
import org.example.dto.CommentUpdateRequestDto;
import org.example.model.User;
import org.example.model.api_model.Comment;
import org.example.model.api_model.Movie;
import org.example.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{imdbId}")
    public ResponseEntity<Movie> createComment(@RequestBody CommentDto commentDt,
                                               @PathVariable("imdbId") String imdbId,
                                               Authentication authentication) {
        log.info("Create comment");
        User currentUser = authentication.getPrincipal() instanceof User ?
                (User) authentication.getPrincipal() : null;
        return ResponseEntity.ok(commentService.saveComment(commentDt, imdbId, currentUser));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId,
                                           @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
                                           Authentication authentication) {
        log.info("Update comment");
        User currentUser = authentication.getPrincipal() instanceof User ?
                (User) authentication.getPrincipal() : null;
        if (commentService.handleCommentAuthorization(commentId, currentUser) != null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        commentService.update(commentId, commentUpdateRequestDto);
        return ResponseEntity.ok("Comment updated successfully");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId,
                                           Authentication authentication) {
        log.info("Delete comment");
        User currentUser = authentication.getPrincipal() instanceof User ?
                (User) authentication.getPrincipal() : null;
        if (commentService.handleCommentAuthorization(commentId, currentUser) != null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        commentService.deleteCommentById(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
