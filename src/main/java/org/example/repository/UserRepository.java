package org.example.repository;

import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    boolean existsByUserName(String username);

    Optional<User> findByUserName(String username);

}
