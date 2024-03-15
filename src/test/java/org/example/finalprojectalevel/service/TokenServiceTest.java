package org.example.finalprojectalevel.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Token;
import org.example.repository.TokenRepository;
import org.example.service.TokenService;
import org.example.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RequiredArgsConstructor
public class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;
    @InjectMocks
    @Autowired
    private TokenService tokenService;

    @BeforeEach
    public void setUp() {
        tokenRepository = Mockito.mock(TokenRepository.class);
        tokenService = new TokenServiceImpl(tokenRepository);
    }

    @Test
    public void testCreate() {
        // Arrange
        Token token = Token.builder()
                .token("token")
                .isExpired(false)
                .build();

        when(tokenRepository.save(token)).thenReturn(token);
        // Act
        tokenService.create(token);
        // Assert
        verify(tokenRepository).save(token);
    }

    @Test
    public void testFindAllByUser_Id() {
        // Arrange
        Long userId = 123L;
        List<Token> expectedTokens = Arrays.asList(new Token(), new Token());

        when(tokenRepository.findAllByUser_Id(userId)).thenReturn(expectedTokens);

        List<Token> actualTokens = tokenService.findAllByUser_Id(userId);

        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testFindByValidToken() {
        final var validToken = "validToken";
        Token expectedToken = Token.builder()
                .token(validToken)
                .isExpired(false)
                .build();

        when(tokenRepository.findByToken(validToken)).thenAnswer(invocation -> {
            String tokenArg = invocation.getArgument(0);
            if (validToken.equals(tokenArg)) {
                return Optional.of(expectedToken);
            } else {
                return Optional.empty();
            }
        });

        Token actualToken = tokenService.findByToken(validToken);

        assertEquals(expectedToken, actualToken);
    }

    @Test
    public void testDeleteByToken_existingToken() {
        final var existingToken = "existingToken";
        Mockito.doNothing().when(tokenRepository).deleteByToken(existingToken);

        // Call the method to be tested
        tokenService.deleteByToken(existingToken);

        // Verify that the method was called with the correct token
        Mockito.verify(tokenRepository, Mockito.times(1)).deleteByToken(existingToken);
    }

    @Test
    public void testUpdateToken() {
        String validToken = "validToken111";
        Token token = Token.builder()
                .token(validToken)
                .isExpired(false)
                .build();
        when(tokenRepository.save(token)).thenReturn(token);

        // Call the method to be teste
        tokenService.update(token);

        verify(tokenRepository).save(token);
    }
}
