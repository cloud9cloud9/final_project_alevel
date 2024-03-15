package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserUpdateRequestDto;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void create(@NonNull final User userEntity) {
        if (userRepository.existsByUserName(userEntity.getUsername())) {
            throw new RuntimeException("User with this username already exists");
        }
        userRepository.save(userEntity);
    }

    @Override
    public void update(@NonNull final Long id,
                       @NonNull User user) {
        User fUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        fUser.setUserName(user.getUsername());
        fUser.setEmail(user.getEmail());
        userRepository.save(fUser);
    }

    @Override
    public void update(@NonNull final Long id,
                       @NonNull UserUpdateRequestDto requestUser) {
        User upUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        upUser.setUserName(requestUser.userName());
        upUser.setEmail(requestUser.email());
        userRepository.save(upUser);
    }

    @Override
    public void delete(@NonNull final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(@NonNull final Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUserName(@NonNull String userName) {
        return userRepository.findByUserName(userName);
    }
}
