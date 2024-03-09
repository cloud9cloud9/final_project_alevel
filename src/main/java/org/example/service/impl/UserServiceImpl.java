package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void create(User userEntity) {
        log.info("User created: {}", userEntity);
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            throw new RuntimeException("User with this username already exists");
        }
        userRepository.save(userEntity);
    }

    @Override
    public void update(User userEntity) {
        log.info("User updated: {}", userEntity);
        userRepository.save(userEntity);

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }
}
