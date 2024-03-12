package org.example.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "user name must not be empty, and will be unique in the system")
    @Schema(example = "overpathz",
            description = "user name associated with user account already created in the system")
    private String userName;

    @NotBlank(message = "password must not be empty")
    @Schema(example = "test123",
            description = "password corresponding to provided email-id")
    private String password;

}
