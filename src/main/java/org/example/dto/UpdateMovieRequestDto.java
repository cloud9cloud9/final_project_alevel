package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@JacksonStdImpl
@Builder
public class UpdateMovieRequestDto {

    @Schema(example = "tt0120804", description = "IMDB ID")
    private String imdbId;

    @Schema(example = "Spider-Man", description = "Set \"N/A\" if the title of the movie is unknown")
    private String title;

    @Schema(example = "2001", description = "Set \"N/A\" if the year of the movie is unknown")
    private String year;

    @Schema(example = "movie, episode", description = "Set \"N/A\" if the type of the movie is unknown")
    private String type;

    @Schema(example = "https://m.media-amazon.com/images/M/MV5BMTkxNjUxODY3NF5BMl5BanBnXkFtZTcwMjQyMzMzMg@@._V1_SX300.jpg")
    private String poster;

}
