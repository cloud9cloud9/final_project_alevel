package org.example.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ExceptionResponseDto;
import org.example.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;


@ControllerAdvice
@Slf4j
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<?> movieNotFoundException(MovieNotFoundException ex) {
        log.error("Exception Caught:", ex);
        final var response = new HashMap<String, String>();
        response.put("message", "Your movie was not found");
        response.put("error", ex.getClass().getSimpleName());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(DeleteMovieException.class)
    public ResponseEntity<?> deleteMovieExceptionHandler(DeleteMovieException ex) {
        log.error("Exception Caught:", ex);
        final var response = new HashMap<String, String>();
        response.put("message", "Failed to delete movie, id movie not found");
        response.put("error", ex.getClass().getSimpleName());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<?> commentNotFoundException(CommentNotFoundException ex) {
        log.error("Exception Caught:", ex);
        final var response = new HashMap<String, String>();
        response.put("message", "Your comment was not found");
        response.put("error", ex.getClass().getSimpleName());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(FavoriteMovieNotFoundException.class)
    public ResponseEntity<?> favoriteMovieNotFoundException(FavoriteMovieNotFoundException ex) {
        log.error("Exception Caught:", ex);
        final var response = new HashMap<String, String>();
        response.put("message", "Your favorite movie was not found");
        response.put("error", ex.getClass().getSimpleName());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler({EmptyFavoriteMoviesException.class})
    public ResponseEntity<?> emptyFavoriteMoviesException(EmptyFavoriteMoviesException ex) {
        log.error("Exception Caught:", ex);
        final var response = new HashMap<String, String>();
        response.put("message", "Your favorite movies list is empty");
        response.put("error", ex.getClass().getSimpleName());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> userNotFoundException(UserNotFoundException ex) {
        log.error("Exception Caught:", ex);
        final var response = new HashMap<String, String>();
        response.put("message", "User not found");
        response.put("error", ex.getClass().getSimpleName());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler({UserRegistrationException.class})
    public ResponseEntity<ExceptionResponseDto<String>> userRegistrationException(UserRegistrationException ex) {
        log.error("Exception Caught:", ex);
        ExceptionResponseDto<String> errorResponse = new ExceptionResponseDto<>();
        errorResponse.setStatus(HttpStatus.CONFLICT.toString());
        errorResponse.setDescription("User registration failed");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<ExceptionResponseDto<String>> invalidTokenException(InvalidTokenException ex) {
        log.error("Exception Caught:", ex);
        ExceptionResponseDto<String> errorResponse = new ExceptionResponseDto<>();
        errorResponse.setStatus(HttpStatus.FORBIDDEN.toString());
        errorResponse.setDescription("Invalid token");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
