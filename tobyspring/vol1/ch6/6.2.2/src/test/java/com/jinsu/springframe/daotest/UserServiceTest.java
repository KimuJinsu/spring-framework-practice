package com.jinsu.springframe.daotest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.daotest.UserServiceTest.TestUserService;
import com.jinsu.springframe.daotest.UserServiceTest.TestUserServiceException;
import com.jinsu.springframe.domain.Level;
import com.jinsu.springframe.domain.User;
import com.jinsu.springframe.service.UserService;
import com.jinsu.springframe.service.UserServiceImpl;
import com.jinsu.springframe.service.UserServiceTx;

import static com.jinsu.springframe.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.jinsu.springframe.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Spring의 확장 기능을 사용하여 JUnit5 테스트 클래스에 스프링 기능을 통합
@ExtendWith(SpringExtension.class) 
//스프링 컨텍스트를 설정할 때 사용할 구성 클래스 지정
@ContextConfiguration(classes = {TestServiceFactory.class})
public class UserServiceTest {

 // userServiceImplement 필드를 자동으로 주입받음
 @Autowired 
 UserService userServiceImplement;

 // @Qualifier를 사용하여 특정 빈(userService2)을 주입받음
 @Autowired 
 @Qualifier("userService2")
 UserService userServiceTx;

 // UserDao를 자동으로 주입받음
 @Autowired 
 UserDao userDao;

 // MailSender를 자동으로 주입받음
 @Autowired 
 MailSender mailSender;

 // PlatformTransactionManager를 자동으로 주입받음
 @Autowired 
 PlatformTransactionManager transactionManager;

 // 테스트에 사용될 사용자 목록을 정의
 static List<User> users;

 // 각 테스트 메서드 실행 전에 사용자 목록을 초기화
 @BeforeEach
 public void setUp() {	
     users = Arrays.asList(
         new User("madnite1", "이상호", "p4", "intheeast1009@gmail.com", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
         new User("bumjin", "박범진", "p1", "intheeast0305@gmail.com", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
         new User("joytouch", "강명성", "p2", "kitec403@gmail.com", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
         new User("erwins", "신승한", "p3", "intheeast0725@gmail.com", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
         new User("green", "오민규", "p5", "intheeast@gmail.com", Level.GOLD, 100, Integer.MAX_VALUE)
     );
 }

 // 사용자 레벨 업그레이드 기능을 테스트하는 메서드
 @Test
 public void upgradeLevels() throws Exception {
     userDao.deleteAll();  // 테스트 전에 모든 사용자 데이터를 삭제
     for(User user : users) userDao.add(user);  // 초기화한 사용자 데이터를 DB에 추가

     userServiceTx.upgradeLevels();  // 트랜잭션 적용된 업그레이드 기능 실행

     checkLevelUpgraded(users.get(0), true);  // 테스트 사용자가 업그레이드 되었는지 확인
     checkLevelUpgraded(users.get(1), false);
     checkLevelUpgraded(users.get(2), true);
     checkLevelUpgraded(users.get(3), false);
     checkLevelUpgraded(users.get(4), false);
 }

 // MockMailSender 클래스는 실제 이메일 전송 대신에 호출을 기록하기 위한 목적으로 사용
 static class MockMailSender implements MailSender {
     private List<String> requests = new ArrayList<String>();

     public List<String> getRequests() {
         return requests;
     }

     // 이메일 전송을 기록
     public void send(SimpleMailMessage mailMessage) throws MailException {
         requests.add(mailMessage.getTo()[0]);  
     }

     public void send(SimpleMailMessage[] mailMessage) throws MailException {}
 }

 // 사용자 레벨 업그레이드를 확인하는 헬퍼 메서드
 private void checkLevelUpgraded(User user, boolean upgraded) {
     Optional<User> optionalUser = userDao.get(user.getId());
     if (!optionalUser.isEmpty()) {
         User userUpdate = optionalUser.get();
         if (upgraded) {
             assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
         } else {
             assertEquals(userUpdate.getLevel(), (user.getLevel()));
         }			
     }		
 }

 // 사용자 추가 기능을 테스트하는 메서드
 @Test
 public void add() {
     userDao.deleteAll();  // 테스트 전에 모든 사용자 데이터를 삭제
     
     User userWithLevel = users.get(4);	  // 레벨이 지정된 사용자
     User userWithoutLevel = users.get(0);  // 레벨이 지정되지 않은 사용자
     userWithoutLevel.setLevel(null);

     userServiceTx.add(userWithLevel);  // 사용자 추가
     userServiceTx.add(userWithoutLevel);

     Optional<User> optionalUserWithLevelRead = userDao.get(userWithLevel.getId());
     if (!optionalUserWithLevelRead.isEmpty()) {
         User userWithLevelRead = optionalUserWithLevelRead.get();
         assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel()); 
     }		
		
     Optional<User> optionalUserWithoutLevelRead = userDao.get(userWithoutLevel.getId());
     if (!optionalUserWithoutLevelRead.isEmpty()) {
         User userWithoutLevelRead = optionalUserWithoutLevelRead.get();
         assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
     }		
 }

 // 트랜잭션 처리 도중에 예외가 발생하는지 테스트
 @Test
 public void upgradeAllOrNothing() throws Exception {
     TestUserService testUserService = 
             new TestUserService(users.get(2).getId()); 		
		
     testUserService.setUserService(this.userServiceImplement);
     testUserService.setTransactionManager(this.transactionManager);
     testUserService.setUsImpl((UserServiceImpl) testUserService.getUserService());

     userDao.deleteAll(); 
     for(User user : users) userDao.add(user);

     try {
         testUserService.upgradeLevels();  // 트랜잭션 테스트 실행
     } catch(TestUserServiceException e) { 
         e.printStackTrace();
         fail("TestUserServiceException expected"); 
     }
     
     checkLevelUpgraded(users.get(1), false);
 }

 // 트랜잭션 처리를 위한 UserServiceTx를 상속받아 예외 발생 시 롤백 기능을 테스트
 static class TestUserService extends UserServiceTx {
     private String id;
     UserServiceImpl usImpl;
     
     private TestUserService(String id) {  
         this.id = id;
     }
     
     public void setUsImpl(UserServiceImpl usImpl) {
         this.usImpl = usImpl;
     }
     
     @Override
     public void upgradeLevels() {
         TransactionStatus status = this.getTransactionManager().
                 getTransaction(new DefaultTransactionDefinition());					
         
         try {
             for (User user : users) {
                 if (usImpl.canUpgradeLevel(user)) {
                     upgradeLevel(user);
                 }
             }
             this.getTransactionManager().commit(status);
         } catch (RuntimeException e) {
             this.getTransactionManager().rollback(status);
             throw e;
         }
     }

     // 특정 사용자에 대해 예외를 발생시켜 롤백을 유도하는 메서드
     protected void upgradeLevel(User user) {
         if (user.getId().equals(this.id)) 
             throw new TestUserServiceException();  
         this.usImpl.upgradeLevel(user);			
     }
 }

 // 예외 클래스를 정의
 static class TestUserServiceException extends RuntimeException {
 }
}
		