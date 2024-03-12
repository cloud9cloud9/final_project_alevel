package org.example.mapper;

import org.example.dto.CommentDto;
import org.example.dto.MovieDto;
import org.example.model.api_model.Comment;
import org.example.model.api_model.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapper {

    public MovieDto toMovieDto(Movie movie) {
        return MovieDto.builder()
                .imdbId(movie.getImdbId())
                .year(movie.getYear())
                .title(movie.getTitle())
                .poster(movie.getPoster())
                .type(movie.getType())
                .comments(toMovieDtoCommentDto(movie.getComments()))
                .build();
    }

    public List<MovieDto> toMovieDto(List<Movie> movies) {
        return movies.stream()
                .map(this::toMovieDto)
                .toList();
    }

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .authorId(comment.getAuthorId())
                .text(comment.getText())
                .timestamp(comment.getTimestamp())
                .build();
    }

    public List<CommentDto> toMovieDtoCommentDto(List<Comment> comments) {
        return comments.stream()
                .map(this::toCommentDto)
                .toList();
    }

    public Movie toMovie(MovieDto movieDto) {
        return Movie.builder()
                .imdbId(movieDto.getImdbId())
                .year(movieDto.getYear())
                .title(movieDto.getTitle())
                .poster(movieDto.getPoster())
                .type(movieDto.getType())
                .comments(new ArrayList<>())
                .build();
    }

    public Comment toMovieComment(CommentDto commentDto) {
        return Comment.builder()
                .authorId(commentDto.getAuthorId())
                .text(commentDto.getText())
                .timestamp(commentDto.getTimestamp())
                .build();
    }

    public List<Comment> toMovieComment(List<CommentDto> commentDtos) {
        return commentDtos.stream()
                .map(this::toMovieComment)
                .toList();
    }
    public List<Movie> toMovie(List<MovieDto> movieDtos) {
        return movieDtos.stream()
                .map(this::toMovie)
                .toList();
    }
}
