package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonStdImpl
@Builder
public class MovieDto {

    @JsonProperty(value = "imdbID")
    String imdbId;

    @JsonProperty(value = "Type")
    String type;

    @JsonProperty(value = "Title")
    String title;

    @JsonProperty(value = "Year")
    String year;

    @JsonProperty(value = "Poster")
    String poster;

    List<CommentDto> comments;
}
