package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonStdImpl
@Builder
public class CommentDto {

    Long authorId;
    String text;
    LocalDate timestamp;

}
