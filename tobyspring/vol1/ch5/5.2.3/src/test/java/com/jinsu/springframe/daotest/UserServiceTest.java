package com.jinsu.springframe.daotest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.domain.Level;
import com.jinsu.springframe.domain.User;
import com.jinsu.springframe.service.UserService;

import static com.jinsu.springframe.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.jinsu.springframe.service.UserService.MIN_RECCOMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class) // Spring의 테스트 지원 기능을 활성화합니다.
@ContextConfiguration(classes = {TestServiceFactory.class}) // 테스트에 사용할 Spring ApplicationContext 설정
public class UserServiceTest {
    @Autowired UserService userService;    // UserService를 자동으로 주입받습니다.
    @Autowired UserDao userDao; // UserDao를 자동으로 주입받습니다.
    @Autowired DataSource dataSource; // DataSource를 자동으로 주입받습니다.
    
    List<User> users;    // 테스트에서 사용할 사용자 데이터 목록
    
    @BeforeEach
    public void setUp() {    
        // 테스트용 사용자 데이터 초기화
        users = Arrays.asList(
            new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
            new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
            new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
            new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
            new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }
    
    @Test
    public void upgradeLevels() throws Exception {
        userDao.deleteAll(); // 데이터베이스의 모든 사용자 데이터 삭제
        for(User user : users) userDao.add(user); // 초기화한 사용자 데이터를 데이터베이스에 추가
        
        userService.upgradeLevels(); // 사용자 레벨 업그레이드 실행
        
        // 각 사용자에 대해 레벨 업그레이드 확인
        checkLevelUpgraded(users.get(0), false); // 레벨 업그레이드 안 됨
        checkLevelUpgraded(users.get(1), true);  // 레벨 업그레이드 됨
        checkLevelUpgraded(users.get(2), false); // 레벨 업그레이드 안 됨
        checkLevelUpgraded(users.get(3), true);  // 레벨 업그레이드 됨
        checkLevelUpgraded(users.get(4), false); // 레벨 업그레이드 안 됨
    }
    
    private void checkLevelUpgraded(User user, boolean upgraded) {
        Optional<User> optionalUser = userDao.get(user.getId()); // 데이터베이스에서 사용자 정보 조회
        if (!optionalUser.isEmpty()) { // 사용자가 존재하면
            User userUpdate = optionalUser.get(); // 조회된 사용자 정보
            if (upgraded) {
                assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel()); // 레벨 업그레이드 확인
            } else {
                assertEquals(userUpdate.getLevel(), user.getLevel()); // 레벨이 유지되었는지 확인
            }            
        }        
    }
    
    @Test
    public void add() {
        userDao.deleteAll(); // 데이터베이스의 모든 사용자 데이터 삭제
        
        User userWithLevel = users.get(4);    // GOLD 레벨 사용자
        User userWithoutLevel = users.get(0); // 레벨이 없는 사용자
        userWithoutLevel.setLevel(null); // 레벨을 null로 설정
        
        userService.add(userWithLevel);    // 사용자 추가
        userService.add(userWithoutLevel); // 사용자 추가
        
        // GOLD 레벨 사용자 정보 조회 및 확인
        Optional<User> optionalUserWithLevelRead = userDao.get(userWithLevel.getId());
        if (!optionalUserWithLevelRead.isEmpty()) {
            User userWithLevelRead = optionalUserWithLevelRead.get();
            assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel()); 
        }        
        
        // 레벨이 없는 사용자 정보 조회 및 확인
        Optional<User> optionalUserWithoutLevelRead = userDao.get(userWithoutLevel.getId());
        if (!optionalUserWithoutLevelRead.isEmpty()) {
            User userWithoutLevelRead = optionalUserWithoutLevelRead.get();
            assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC); // 레벨이 BASIC으로 설정되어야 함
        }        
    }
    
    @Test
    public void upgradeAllOrNothing() throws Exception {
        // 특정 사용자의 업그레이드 실패 시 전체 트랜잭션을 롤백하기 위한 테스트
        UserService testUserService = new TestUserService(users.get(3).getId());  // 실패할 사용자의 ID
        testUserService.setUserDao(this.userDao); // UserDao 설정
        testUserService.setDataSource(this.dataSource); // DataSource 설정
         
        userDao.deleteAll(); // 데이터베이스의 모든 사용자 데이터 삭제
        for(User user : users) userDao.add(user); // 초기화한 사용자 데이터를 데이터베이스에 추가
        
        try {
            testUserService.upgradeLevels(); // 사용자 레벨 업그레이드 실행
            fail("TestUserServiceException expected"); // 예외가 발생해야 함
        } catch(TestUserServiceException e) { 
            // 예외 발생 시 처리 로직 (테스트 통과)
            int a = 0;
            a = 10;
        }
        
        // 레벨 업그레이드 실패로 인해 영향을 받은 사용자의 레벨 확인
        checkLevelUpgraded(users.get(1), false); // 레벨 업그레이드가 안 되어야 함
        //checkLevelUpgraded(users.get(1), true); // 주석 처리된 부분, 레벨 업그레이드가 되어야 함
    }
    
    // 테스트를 위해 UserService를 확장한 클래스
    // 스태틱 네스티드 래스는 아웃터 클래스 메서드에서 생성 할 수 있다... 멤버라서 프라이빗을 사용 할 수 있다.
    static class TestUserService extends UserService {
        private String id;
        
        private TestUserService(String id) {  
            this.id = id;
        }

        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) 
                throw new TestUserServiceException();  // 특정 사용자에게 예외 발생
            super.upgradeLevel(user);  // 부모 클래스의 메서드 호출
        }
    }
    
    // 테스트용 예외 클래스
    static class TestUserServiceException extends RuntimeException {
    }
}