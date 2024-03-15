package org.example.finalprojectalevel.repository;

import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RequiredArgsConstructor
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    public void testSave() {

        var user = User.builder()
                .userName("save")
                .password("save")
                .email("save")
                .build();
        repository.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setUserName("findById");
        user.setPassword("findById");
        user.setEmail("findById");
        // Act
        repository.save(user);
        User savedUser = repository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        // Assert
        assertNotNull(savedUser);
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    public void testExistsByUserName() {
        User user = new User();
        user.setUserName("existsByUserName");
        user.setPassword("existsByUserName");
        user.setEmail("existsByUserName");
        repository.save(user);
        assertTrue(repository.existsByUserName(user.getUsername()));
    }

    @Test
    public void testFindAll() {
        User user = new User();
        user.setUserName("findAll");
        user.setPassword("findAll");
        user.setEmail("findAll");
        repository.save(user);
        List<User> userList = repository.findAll();
        assertNotNull(userList);
        assertFalse(userList.isEmpty());
    }

    @Test
    public void testDeleteById() {
        User user = new User();
        user.setUserName("DeleteById");
        user.setPassword("DeleteById");
        user.setEmail("DeleteById");
        repository.save(user);
        repository.deleteById(user.getId());
    }

    @Test
    public void testFindByUserName() {
        User user = new User();
        user.setUserName("findByUserName");
        user.setPassword("findByUserName");
        user.setEmail("findByUserName   ");
        repository.save(user);
        repository.findByUserName(user.getUsername());

    }
}
