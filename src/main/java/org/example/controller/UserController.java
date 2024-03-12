package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.dto.UserUpdateRequestDto;
import org.example.dto.auth.AuthenticationResponse;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.service.AuthenticationService;
import org.example.service.UserService;
import org.example.util.AuthenticatedUserProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.example.constant.ApiConstantPath.*;


@RequiredArgsConstructor
@Slf4j
@RequestMapping(API_V1_USER)
@RestController
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthenticatedUserProvider authenticatedUserProvider;

    private final AuthenticationService authService;

    @GetMapping(USER + "/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public UserDto getUserById(@PathVariable(required = false) Long id) {
        log.info("Getting user by id: {}", id);
        User user = userService.findById(id);
        return userMapper.toUserDto(user);
    }

    @GetMapping(ADMIN + "/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> getAllUser() {
        log.info("Getting all users");
        Collection<User> all = userService.findAll();
        return userMapper.toUserDto(all);
    }

    @PutMapping(USER)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<AuthenticationResponse> updateUser(@Valid @RequestBody final UserUpdateRequestDto user,
                                                             HttpServletRequest request) throws IOException {
        log.info("Update user: {}", user);
        final var authUserId = authenticatedUserProvider.getUserId(request);
        userService.update(authUserId, user);
        return ResponseEntity.ok(authService.refreshTokenUpdatedUser(authUserId));
    }

    @DeleteMapping(USER)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        final var authUserId = authenticatedUserProvider.getUserId(request);
        log.info("Delete user by id: {}", authUserId);
        userService.delete(authUserId);
        return ResponseEntity.ok().build();
    }
}
