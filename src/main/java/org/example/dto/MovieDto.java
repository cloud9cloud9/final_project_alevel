package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonStdImpl
@Builder
public class MovieDto {

    @Schema(description = "IMDB ID")
    @JsonProperty(value = "imdbID")
    private String imdbId;

    @Schema(description = "Type")
    @JsonProperty(value = "Type")
    private String type;

    @Schema(description = "Title")
    @JsonProperty(value = "Title")
    private String title;

    @Schema(description = "Year")
    @JsonProperty(value = "Year")
    private String year;

    @Schema(description = "URL to the movie poster")
    @JsonProperty(value = "Poster")
    private String poster;

    @Schema(description = "List of comments associated with the movie")
    private List<CommentDto> comments;

}
