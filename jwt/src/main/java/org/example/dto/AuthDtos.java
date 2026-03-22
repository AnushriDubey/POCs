package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.entity.Role;

public class AuthDtos {

    public record RegisterRequest(
            @NotBlank(message = "full name is required")
            String fullName,
            @Email(message = "Invalid email format")
            @NotBlank(message = "Email is required")
            String email,
            @NotBlank(message = "password is required")
            @Size(min = 8, message = "Password must be at least 8 characters")
            String password,
            Role role    // Optional: defaults to USER if null
    ) {}

    public record LoginRequest(
            @Email @NotBlank String email,
            @NotBlank String password
    ){}

    public record RefreshRequest(
            @NotBlank(message = "Refresh token is required")
            String refreshToken
    ) {}

    public record AuthResponse(
            String accessToken,
            String refreshToken,
            long accessTokenExpiresIn,   // Milliseconds
            String tokenType,
            UserInfo user
    ) {
        public static AuthResponse of(String accessToken, String refreshToken,
                                      long expiresIn, UserInfo user) {
            return new AuthResponse(accessToken, refreshToken, expiresIn, "Bearer", user);
        }
    }

    public record AuthResponse(String accessToken,
                               String refreshToken,
                               long accessTokenExpiresIn,
                               String tokenType,
                               UserInfo userInfo){

    }
}
