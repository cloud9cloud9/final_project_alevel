package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMovieDto {

    @Schema(example = "tt0120804")
    @NotBlank
    private String imdbId;

    @Schema(example = "Spider-Man",
            description = "Set \"N/A\" if the title of the movie is unknown")
    @NotBlank
    private String title;

    @Schema(example = "2001",
            description = "Set \"N/A\" if the year of the movie is unknown")
    @NotBlank
    private String year;

    @Schema(example = "https://m.media-amazon.com/images/M/MV5BMTkxNjUxODY3NF5BMl5BanBnXkFtZTcwMjQyMzMzMg@@._V1_SX300.jpg")
    private String poster;

}
