package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.exception.InvalidTokenException;
import org.example.model.Token;
import org.example.repository.TokenRepository;
import org.example.service.TokenService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void create(@NonNull Token token) {
        tokenRepository.save(token);
    }

    @Override
    public List<Token> findAllByUser_Id(@NonNull final Long userId) {
        return tokenRepository.findAllByUser_Id(userId);
    }

    @Override
    public Token findByToken(@NonNull String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);
    }

    @Override
    public void deleteByToken(@NonNull String token) {
        tokenRepository.deleteByToken(token);
    }

    @Override
    public void update(@NonNull Token token) {
        tokenRepository.save(token);
    }
}
