package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonStdImpl
@Builder
public class CommentDto {

    @Schema(description = "Authors id", required = true)
    @NotBlank
    private Long authorId;

    @Schema(description = "Text comments", required = true)
    private String text;

    @Schema(description = "Date of creation")
    private LocalDate timestamp;

}
