package org.example.dto;

public record UpdateMovieRequestDto(String imdbId, String title, String year, String type, String poster) {

}
