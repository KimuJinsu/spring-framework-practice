package com.jinsu.springframe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jinsu.springframe.domain.User;

@SpringJUnitConfig(locations = "/test-applicationContext.xml")
@DirtiesContext
public class UserDaoTest {

    @Autowired
    private ApplicationContext context;
    
    private UserDao dao; 
    
    private User user1;
    private User user2;
    private User user3;
    
    @BeforeEach
    public void setUp() {
        this.dao = this.context.getBean(UserDao.class);
        
        this.user1 = new User("gyumee", "Kim", "springno1");
        this.user2 = new User("leegw700", "Lee", "springno2");
        this.user3 = new User("bumjin", "Bum", "springno3");
    }
    
    @Test 
    public void addAndGet() throws SQLException {                
        dao.deleteAll();
        assertEquals(0, dao.getCount());
        
        dao.add(user1);
        dao.add(user2);
        assertEquals(2, dao.getCount());
        
        Optional<User> optUserGet1 = dao.get(user1.getId());
        assertTrue(optUserGet1.isPresent());
        User userget1 = optUserGet1.get();
        assertEquals(user1.getName(), userget1.getName());
        assertEquals(user1.getPassword(), userget1.getPassword());
        
        Optional<User> optUserGet2 = dao.get(user2.getId());
        assertTrue(optUserGet2.isPresent());
        User userget2 = optUserGet2.get();
        assertEquals(user2.getName(), userget2.getName());
        assertEquals(user2.getPassword(), userget2.getPassword());
    }

    @Test
    public void getUserFailure() {
        dao.deleteAll();
        assertEquals(0, dao.getCount());
        
        Optional<User> user = dao.get("unknown_id");
        assertTrue(user.isEmpty());
    }

    @Test
    public void count() {
        dao.deleteAll();
        assertEquals(0, dao.getCount());
                
        dao.add(user1);
        assertEquals(1, dao.getCount());
        
        dao.add(user2);
        assertEquals(2, dao.getCount());
        
        dao.add(user3);
        assertEquals(3, dao.getCount());
    }
    
    @Test
    public void getAll()  {
        dao.deleteAll();
        
        List<User> users0 = dao.getAll();
        assertEquals(0, users0.size());
        
        dao.add(user1); // Id: gyumee
        List<User> users1 = dao.getAll();
        assertEquals(1, users1.size());
        checkSameUser(user1, users1.get(0));
        
        dao.add(user2); // Id: leegw700
        List<User> users2 = dao.getAll();
        assertEquals(2, users2.size());
        checkSameUser(user1, users2.get(0));  
        checkSameUser(user2, users2.get(1));
        
        dao.add(user3); // Id: bumjin
        List<User> users3 = dao.getAll();
        assertEquals(3, users3.size());
        checkSameUser(user1, users3.get(1));  
        checkSameUser(user2, users3.get(2));  
        checkSameUser(user3, users3.get(0));  
    }

    private void checkSameUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getPassword(), user2.getPassword());
    }
}