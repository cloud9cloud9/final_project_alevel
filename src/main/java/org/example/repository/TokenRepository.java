package org.example.repository;

import org.example.model.Token;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends BaseRepository<Token> {

    List<Token> findAllByUser_Id(Long id);

    void deleteAllByUser_Id(Long userId);

    Token findByToken(String token);

    void deleteByToken(String token);

}
