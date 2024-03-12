package org.example.dto;

import org.example.model.UserRole;

public record UserDto(Long id, String userName, String email, UserRole role) {

}
