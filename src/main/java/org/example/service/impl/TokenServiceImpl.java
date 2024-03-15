package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.exception.InvalidTokenException;
import org.example.model.Token;
import org.example.repository.TokenRepository;
import org.example.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void create(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public List<Token> findAllByUser_Id(Long userId) {
        return tokenRepository.findAllByUser_Id(userId);
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);
    }

    @Override
    public void deleteByToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Override
    public void update(Token token) {
        tokenRepository.save(token);
    }
}
