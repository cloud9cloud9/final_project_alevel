package org.example.finalprojectalevel.repository;

import jakarta.transaction.Transactional;
import org.example.model.Token;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.repository.TokenRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenRepositoryTest {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testSave() {
        // Arrange
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");
        user.setEmail("test");
        user.setRole(UserRole.ROLE_USER);
        user = userRepository.save(user);

        Token token = Token.builder()
                .token("test_token")
                .isExpired(false)
                .user(user)
                .build();
        // Act
        Token savedToken = tokenRepository.save(token);
        // Assert
        assertNotNull(savedToken);
        assertEquals(token.getToken(), savedToken.getToken());
        assertEquals(token.isExpired(), savedToken.isExpired());
        assertEquals(token.getUser(), savedToken.getUser());
    }

    @Test
    public void testFindAllByUserId() {
        // Arrange
        User user = new User();
        user.setUserName("testFind");
        user.setPassword("testFind");
        user.setEmail("testFind");
        user.setRole(UserRole.ROLE_USER);
        user = userRepository.save(user);

        Token token1 = Token.builder()
                .token("test_token1")
                .isExpired(false)
                .user(user)
                .build();
        tokenRepository.save(token1);

        Token token2 = Token.builder()
                .token("test_token2")
                .isExpired(false)
                .user(user)
                .build();
        tokenRepository.save(token2);

        // Act
        List<Token> tokens = tokenRepository.findAllByUser_Id(user.getId());

        // Assert
        assertNotNull(tokens);
        assertEquals(2, tokens.size());
    }

    @Test
    public void testFindByToken() {
        // Arrange
        Token token = Token.builder()
                .token("test_token3")
                .isExpired(false)
                .build();
        tokenRepository.save(token);

        // Act
        Optional<Token> foundToken = tokenRepository.findByToken("test_token3");

        // Assert
        assertNotNull(foundToken);
        assertEquals(token.getToken(), foundToken.get().getToken());
        assertEquals(token.isExpired(), foundToken.get().isExpired());
    }

    @Transactional
    @Test
    public void testDeleteByToken() {
        // Arrange
        Token token = Token.builder()
                .token("test_token4")
                .isExpired(false)
                .build();
        tokenRepository.save(token);

        // Act
        Optional<Token> foundTokenOptional = tokenRepository.findByToken(token.getToken());
        if (foundTokenOptional.isPresent()) {
            Token foundToken = foundTokenOptional.get();
            tokenRepository.delete(foundToken);

            // Assert
            Optional<Token> deletedTokenOptional = tokenRepository.findByToken(foundToken.getToken());
            assertFalse(deletedTokenOptional.isPresent());
        }
    }
}
