package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.ApiConstantPath;
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


@RequiredArgsConstructor
@Slf4j
@RequestMapping(ApiConstantPath.API_V1_USER)
@RestController
@Tag(name = "User Management", description = "Endpoints for managing user profile details")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthenticatedUserProvider authenticatedUserProvider;

    private final AuthenticationService authenticationService;

    @GetMapping(ApiConstantPath.USER + "/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "Get user details by id",
            description = "Get user details by id, registered in the system")
    public UserDto getUserById(@PathVariable(required = false) Long id) {
        log.info("Getting user by id: {}", id);
        User user = userService.findById(id);
        return userMapper.toUserDto(user);
    }

    @GetMapping(ApiConstantPath.ADMIN + "/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all users",
            description = "Get all users, registered in the system, only for admin users")
    public List<UserDto> getAllUser() {
        log.info("Getting all users");
        Collection<User> all = userService.findAll();
        return userMapper.toUserDto(all);
    }

    @PutMapping(ApiConstantPath.USER)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "Update user details",
            description = "Update user details, registered in the system, only for authenticated users")
    public ResponseEntity<AuthenticationResponse> updateUser(@Valid @RequestBody final UserUpdateRequestDto user,
                                                             HttpServletRequest request) throws IOException {
        log.info("Update user: {}", user);
        final var authUserId = authenticatedUserProvider.getUserId(request);
        userService.update(authUserId, user);
        return ResponseEntity.ok(authenticationService.refreshTokenUpdatedUser(authUserId));
    }

    @DeleteMapping(ApiConstantPath.USER)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "Delete user",
            description = "Delete user, registered in the system, only for authenticated users")
    public void deleteUser(HttpServletRequest request) {
        final var authUserId = authenticatedUserProvider.getUserId(request);
        log.info("Delete user by id: {}", authUserId);
        userService.delete(authUserId);
    }
}
