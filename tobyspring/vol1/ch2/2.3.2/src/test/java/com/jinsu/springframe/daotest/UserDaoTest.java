package com.jinsu.springframe.daotest;



import java.sql.SQLException;


import com.jinsu.springframe.dao.DaoFactory;
import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.domain.User;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.Assertions;


public class UserDaoTest {
	
	@Test 
	public void andAndGet() throws SQLException {
		ApplicationContext context =
				new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		dao.deleteAll();
		Assertions.assertEquals(dao.getCount(), 0);
		
		User user = new User();
		user.setId("gyumee");
		user.setName("good");
		user.setPassword("springno1");

		dao.add(user);
		Assertions.assertEquals(dao.getCount(), 1);
		
		User user2 = dao.get(user.getId());
		
		Assertions.assertEquals(user2.getName(), user.getName());
		Assertions.assertEquals(user2.getPassword(), user.getPassword());
	}
}