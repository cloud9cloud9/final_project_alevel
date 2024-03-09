package org.example.repository;

import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
