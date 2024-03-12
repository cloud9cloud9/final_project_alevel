package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import lombok.*;
import org.example.model.api_model.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonStdImpl
@Builder
public class MovieResponse {

    private String imdbId;
    private String title;
    private String year;
    private String type;
    private String poster;
    private List<Comment> comments = new ArrayList<>();

}
