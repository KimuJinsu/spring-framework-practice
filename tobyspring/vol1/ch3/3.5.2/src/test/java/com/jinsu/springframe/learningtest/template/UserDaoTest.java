package com.jinsu.springframe.learningtest.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.domain.User;

@SpringJUnitConfig(locations="/test-applicationContext.xml")
@DirtiesContext
public class UserDaoTest {

    @Autowired
    ApplicationContext context;
    
    private UserDao dao; 
    
    private User user1;
    private User user2;
    private User user3;
    
    @BeforeEach
    public void setUp() {
        this.dao = this.context.getBean("userDao", UserDao.class);
        
        this.user1 = new User("gyumee", "¹Ú¼ºÃ¶", "springno1");
        this.user2 = new User("leegw700", "ÀÌ±æ¿ø", "springno2");
        this.user3 = new User("bumjin", "¹Ú¹üÁø", "springno3");
    }
    
    @Test 
    public void andAndGet() throws SQLException {        
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.add(user1);
        dao.add(user2);
        assertEquals(dao.getCount(), 2);
        
        User userget1 = dao.get(user1.getId());
        assertEquals(userget1.getName(), user1.getName());
        assertEquals(userget1.getPassword(), user1.getPassword());
        
        User userget2 = dao.get(user2.getId());
        assertEquals(userget2.getName(), user2.getName());
        assertEquals(userget2.getPassword(), user2.getPassword());
    }

    @Test
    public void getUserFailure() throws SQLException {
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);
        
        assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unknown_id");
        });
    }

    @Test
    public void count() throws SQLException {
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);
                
        dao.add(user1);
        assertEquals(dao.getCount(), 1);
        
        dao.add(user2);
        assertEquals(dao.getCount(), 2);
        
        dao.add(user3);
        assertEquals(dao.getCount(), 3);
    }
}