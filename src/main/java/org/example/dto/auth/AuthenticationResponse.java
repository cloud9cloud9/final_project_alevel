package org.example.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@Schema(title = "TokenSuccessResponse", accessMode = Schema.AccessMode.READ_ONLY)
public class AuthenticationResponse {

    @NotBlank(message = "access token user taken from login request")
    @Schema(description = "Current token",
            example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2bGFkIiwiaWF0IjoxNzEwMDIxODkyLCJleHAiOjE3MTAwMjMzMzJ9.YGVAXDQfWNZNo-B9IY1jNRG1AstS8_EROPO6FT1RDzU")
    private String accessToken;

    @NotBlank(message = "refreshed token , taken by /refresh endpoint")
    @Schema(description = "refreshed token",
            example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2bGFkIiwiaWF0IjoxNzEwMDIxODkyLCJleHAiOjE3MTAwMjMzMzJ9.YGVAXDQfWNZNo-B9IY1jNRG1AstS8_EROPO6FT1RDzU")
    private String refreshToken;

}
