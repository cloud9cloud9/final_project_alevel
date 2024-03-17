package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.ApiConstantPath;
import org.example.dto.CommentDto;
import org.example.dto.CommentUpdateRequestDto;
import org.example.model.User;
import org.example.model.api_model.Movie;
import org.example.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(ApiConstantPath.API_V1_COMMENT)
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{imdbId}")
    @Operation(summary = "Creates a user comments",
            description = "Method provided to add a new comment for a movie")
    public ResponseEntity<Movie> createComment(@RequestBody CommentDto commentDt,
                                               @PathVariable("imdbId") String imdbId,
                                               Authentication authentication) {
        log.info("Create comment");
        User currentUser = authentication.getPrincipal() instanceof User ?
                (User) authentication.getPrincipal() : null;
        return ResponseEntity.ok(commentService.saveComment(commentDt, imdbId, currentUser));
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "Updates a user comments",
            description = "Method provided to update a comment for a movie")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId,
                                           @Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
                                           Authentication authentication) {
        log.info("Update comment");
        User currentUser = (User) authentication.getPrincipal();
        boolean isAuthorized = commentService.canManipulateComment(commentId, currentUser);
        if (isAuthorized) {
            commentService.update(commentId, commentUpdateRequestDto);
            return ResponseEntity.ok("Comment updated successfully");
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @DeleteMapping("/{commentId}")
    @Operation(summary = "Deletes a user comments",
            description = "Method provided to delete a comment for a movie")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId,
                                           Authentication authentication) {
        log.info("Delete comment");
        User currentUser = authentication.getPrincipal() instanceof User ?
                (User) authentication.getPrincipal() : null;
        boolean isAuthorized = commentService.canManipulateComment(commentId, currentUser);
        if (isAuthorized) {
            commentService.deleteCommentById(commentId);
            return ResponseEntity.ok("Comment deleted successfully");
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
