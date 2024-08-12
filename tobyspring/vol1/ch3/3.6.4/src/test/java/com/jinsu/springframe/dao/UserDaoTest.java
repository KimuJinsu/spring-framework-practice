package com.jinsu.springframe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jinsu.springframe.domain.User;

@SpringJUnitConfig(TestDaoFactory.class) // 자바 기반 설정 클래스를 지정
public class UserDaoTest {

    @Autowired
    private UserDao dao; 
    
    private User user1;
    private User user2;
    private User user3;
    
    @BeforeEach
    public void setUp() {
        this.user1 = new User("gyumee", "kimu", "springno1");
        this.user2 = new User("leegw700", "jinsu", "springno2");
        this.user3 = new User("bumjin", "susu", "springno3");
    }
    
    @Test 
    public void andAndGet() {        
        dao.deleteAll();
        assertEquals(0, dao.getCount());

        dao.add(user1);
        dao.add(user2);
        assertEquals(2, dao.getCount());
        
        Optional<User> userget1 = dao.get(user1.getId());
        assertTrue(userget1.isPresent());
        assertEquals(user1.getName(), userget1.get().getName());
        assertEquals(user1.getPassword(), userget1.get().getPassword());
        
        Optional<User> userget2 = dao.get(user2.getId());
        assertTrue(userget2.isPresent());
        assertEquals(user2.getName(), userget2.get().getName());
        assertEquals(user2.getPassword(), userget2.get().getPassword());
    }

    @Test
    public void getUserFailure() {
        dao.deleteAll();
        assertEquals(0, dao.getCount());
        
        assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unknown_id").orElseThrow(() -> new EmptyResultDataAccessException("No user found", 1));
        });
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
        checkSameUser(user3, users3.get(0));  
        checkSameUser(user1, users3.get(1));  
        checkSameUser(user2, users3.get(2));  
    }

    private void checkSameUser(User expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPassword(), actual.getPassword());
    }
}