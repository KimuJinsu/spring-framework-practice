package com.jinsu.springframe.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.domain.Level;
import com.jinsu.springframe.domain.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoFactory.class})
public class UserDaoTest {

    @Autowired UserDao dao;  // UserDao 빈 주입
    @Autowired DataSource dataSource;  // DataSource 빈 주입
    
    private User user1;  // 테스트용 사용자 1
    private User user2;  // 테스트용 사용자 2
    private User user3;  // 테스트용 사용자 3

    @BeforeEach
    public void setUp() {    
        // 테스트 전에 실행되는 메서드로, 테스트용 사용자 데이터를 초기화
        this.user1 = new User("kim", "김진수", "1234", Level.BASIC, 1, 0);
        this.user2 = new User("lee", "이태훈", "5678", Level.SILVER, 55, 10);
        this.user3 = new User("gim", "김민기", "1357", Level.GOLD, 100, 40);
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {                
        dao.deleteAll();  // 모든 사용자 삭제
        assertEquals(dao.getCount(), 0);  // 사용자 수가 0인지 확인
        
        dao.add(user1);  // 사용자 1 추가
        dao.add(user2);  // 사용자 2 추가
        assertEquals(dao.getCount(), 2);  // 사용자 수가 2인지 확인
        
        Optional<User> Optuserget1 = dao.get(user1.getId());  // 사용자 1 조회
        if(!Optuserget1.isEmpty()) {
            User userget = Optuserget1.get();
            assertEquals(user1.getName(), userget.getName());  // 이름 비교
            assertEquals(user1.getPassword(), userget.getPassword());  // 비밀번호 비교
        }
        
        Optional<User> Optuserget2 = dao.get(user2.getId());  // 사용자 2 조회
        if(!Optuserget2.isEmpty()) {
            User userget = Optuserget2.get();
            assertEquals(user2.getName(), userget.getName());  // 이름 비교
            assertEquals(user2.getPassword(), userget.getPassword());  // 비밀번호 비교
        }            
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {        
        dao.deleteAll();  // 모든 사용자 삭제
        assertEquals(dao.getCount(), 0);  // 사용자 수가 0인지 확인

        dao.add(user1);  // 사용자 1 추가
        assertEquals(dao.getCount(), 1);  // 사용자 수가 1인지 확인
        
        dao.add(user2);  // 사용자 2 추가
        assertEquals(dao.getCount(), 2);  // 사용자 수가 2인지 확인
        
        dao.add(user3);  // 사용자 3 추가
        assertEquals(dao.getCount(), 3);  // 사용자 수가 3인지 확인        
    }

    @Test
    public void getUserFailure() throws SQLException, ClassNotFoundException {        
        dao.deleteAll();  // 모든 사용자 삭제
        assertEquals(dao.getCount(), 0);        
        
        Assertions.assertThrows(EmptyResultDataAccessException.class, 
                () -> {dao.get("unknown_id");});  // 존재하지 않는 ID로 조회 시 예외 발생 확인
    }

    @Test
    public void getAll() throws SQLException  {
        dao.deleteAll();  // 모든 사용자 삭제
        List<User> users0 = dao.getAll();  // 모든 사용자 조회
        assertEquals(users0.size(), 0);  // 사용자 수가 0인지 확인
        
        dao.add(user1);  // 사용자 1 추가
        List<User> users1 = dao.getAll();  // 모든 사용자 조회
        assertEquals(users1.size(), 1);  // 사용자 수가 1인지 확인
        checkSameUser(user1, users1.get(0));  // 사용자 정보 확인
        
        dao.add(user2);  // 사용자 2 추가
        List<User> users2 = dao.getAll();  // 모든 사용자 조회
        assertEquals(users2.size(), 2);  // 사용자 수가 2인지 확인
        checkSameUser(user1, users2.get(0));  // 사용자 1 정보 확인
        checkSameUser(user2, users2.get(1));  // 사용자 2 정보 확인
        
        dao.add(user3);  // 사용자 3 추가
        List<User> users3 = dao.getAll();  // 모든 사용자 조회
        assertEquals(users3.size(), 3);  // 사용자 수가 3인지 확인
        checkSameUser(user3, users3.get(0));  // 사용자 3 정보 확인
        checkSameUser(user1, users3.get(1));  // 사용자 1 정보 확인
        checkSameUser(user2, users3.get(2));  // 사용자 2 정보 확인
    }

    private void checkSameUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());  // ID 비교
        assertEquals(user1.getName(), user2.getName());  // 이름 비교
        assertEquals(user1.getPassword(), user2.getPassword());  // 비밀번호 비교
        
        assertEquals(user1.getLevel(), user2.getLevel());  // 레벨 비교
        assertEquals(user1.getLogin(), user2.getLogin());  // 로그인 수 비교
        assertEquals(user1.getRecommend(), user2.getRecommend());  // 추천 수 비교
    }

    @Test
    public void duplciateKey() throws SQLException {
        dao.deleteAll();  // 모든 사용자 삭제
        
        dao.add(user1);  // 사용자 1 추가
        assertThrows(DuplicateKeyException.class, () -> dao.add(user1));  // 동일한 사용자 추가 시 예외 발생 확인
    }

    @Test
    public void sqlExceptionTranslate() {
        dao.deleteAll();  // 모든 사용자 삭제
        
        try {
            dao.add(user1);  // 사용자 1 추가
            dao.add(user1);  // 사용자 1 추가 (중복)
        }
        catch(DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException)ex.getCause();  // 예외 원인 추출
            SQLExceptionTranslator set = 
                    new SQLErrorCodeSQLExceptionTranslator(this.dataSource);  // SQLExceptionTranslator 생성
            DataAccessException transEx = set.translate(null, null, sqlEx);  // 예외 번역
            assertEquals(DuplicateKeyException.class, transEx.getClass());  // 예외 클래스 확인
        }
    }

    @Test
    public void update() {
        dao.deleteAll();  // 모든 사용자 삭제
        
        dao.add(user1);  // 사용자 1 추가
        dao.add(user2);  // 사용자 2 추가
        
        user1.setName("오민규");  // 사용자 1의 정보 수정
        user1.setPassword("springo6");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        dao.update(user1);  // 사용자 1 업데이트
        
        Optional<User> Optuser1update = dao.get(user1.getId());  // 업데이트된 사용자 1 조회
        
        if(!Optuser1update.isEmpty()) {
            User user1update = Optuser1update.get();
            checkSameUser(user1, user1update);  // 사용자 1의 정보 확인
        }   
        
        Optional<User> Optuser2update = dao.get(user2.getId());  // 사용자 2 조회
        
        if(!Optuser2update.isEmpty()) {
            User user2update = Optuser2update.get();
            checkSameUser(user2, user2update);  // 사용자 2의 정보 확인
        }
    }
    
    // @Test
    // public void SimpleGetOne() {
    //    dao.deleteAll();       
    //    dao.add(user1);       
    //    dao.get(user1.getId()); // 테스트 비활성화된 코드
    //}
}