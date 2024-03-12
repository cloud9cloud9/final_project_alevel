package org.example.service;

import org.example.model.Token;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TokenService extends CrudService<Token> {

    List<Token> findAllByUser_Id(Long id);

    Token findByToken(String token);

    void deleteByToken(String token);

    void update(Token token);
}
