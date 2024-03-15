package org.example.mapper;

import org.example.dto.UserDto;
import org.example.model.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class UserMapper {

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole());
    }

    public List<UserDto> toUserDto(Collection<User> users) {
        return users.stream()
                .map(this::toUserDto)
                .toList();
    }
}
