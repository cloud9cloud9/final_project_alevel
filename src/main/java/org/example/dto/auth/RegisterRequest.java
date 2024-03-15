package org.example.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "user name must not be empty, and will be unique in the system")
    @Size(min = 3, max = 20, message = "User name must be between 3 and 20 characters")
    @Schema(example = "overpathz",
            description = "user name associated with user account already created in the system")
    private String userName;

    @NotBlank(message = "password must not be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "secure password to enable user login",
            example = "somethingSecure")
    private String password;

    @NotBlank(message = "email-id must not be empty")
    @Email(message = "email-id must be of valid format")
    @Schema(description = "email-id of user",
            example = "vlad@gmail.com")
    private String email;

}
