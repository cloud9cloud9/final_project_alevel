package org.example.service;

import org.example.dto.UserUpdateRequestDto;
import org.example.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService extends CrudService<User> {

    void update(Long id,
                UserUpdateRequestDto updatedUser);

    Optional<User> findByUserName(String userName);

}
