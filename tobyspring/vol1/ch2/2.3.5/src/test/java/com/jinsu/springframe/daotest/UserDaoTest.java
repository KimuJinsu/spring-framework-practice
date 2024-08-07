package com.jinsu.springframe.daotest;

import java.sql.SQLException;

import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoTest {
    
    private UserDao dao; 
    private User user1;
    private User user2;
    private User user3;
    
    @BeforeEach
    public void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.jinsu.springframe.dao.DaoFactory");
        this.dao = context.getBean(UserDao.class); // Simplified bean retrieval
        
        this.user1 = new User("gyumee", "박성철", "springno1");
        this.user2 = new User("leegw700", "이길원", "springno2");
        this.user3 = new User("bumjin", "박범진", "springno3");
    }
    
    @Test 
    public void andAndGet() throws SQLException {        
        dao.deleteAll();
        Assertions.assertEquals(0, dao.getCount(), "Initial count should be 0");

        dao.add(user1);
        dao.add(user2);
        Assertions.assertEquals(2, dao.getCount(), "Count after adding users should be 2");
        
        User userget1 = dao.get(user1.getId());
        Assertions.assertEquals(user1.getName(), userget1.getName(), "User1 name should match");
        Assertions.assertEquals(user1.getPassword(), userget1.getPassword(), "User1 password should match");
        
        User userget2 = dao.get(user2.getId());
        Assertions.assertEquals(user2.getName(), userget2.getName(), "User2 name should match");
        Assertions.assertEquals(user2.getPassword(), userget2.getPassword(), "User2 password should match");
    }

    @Test
    public void getUserFailure() throws SQLException {
        dao.deleteAll();
        Assertions.assertEquals(0, dao.getCount(), "Initial count should be 0");

        // Using assertThrows to test for expected exception
        Assertions.assertThrows(EmptyResultDataAccessException.class, 
            () -> dao.get("unknown_id"), 
            "Getting a user with unknown_id should throw EmptyResultDataAccessException"
        );
    }
    
    @Test
    public void count() throws SQLException {
        dao.deleteAll();
        Assertions.assertEquals(0, dao.getCount(), "Initial count should be 0");
        
        dao.add(user1);
        Assertions.assertEquals(1, dao.getCount(), "Count after adding first user should be 1");
        
        dao.add(user2);
        Assertions.assertEquals(2, dao.getCount(), "Count after adding second user should be 2");
        
        dao.add(user3);
        Assertions.assertEquals(3, dao.getCount(), "Count after adding third user should be 3");
    }
}