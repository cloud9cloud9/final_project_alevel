package org.example.repository;

import org.example.model.Token;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends BaseRepository<Token> {

    List<Token> findAllByUser_Id(Long id);

    Optional<Token> findByToken(String token);

    void deleteByToken(String token);

}
