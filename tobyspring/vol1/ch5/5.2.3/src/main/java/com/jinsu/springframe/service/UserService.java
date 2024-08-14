package com.jinsu.springframe.service;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.domain.User;
import com.jinsu.springframe.domain.Level;

public class UserService {
    
    // 사용자 레벨 업그레이드를 위한 최소 로그인 수와 추천 수
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    private UserDao userDao; // UserDao를 주입받아 데이터베이스 작업을 수행

    // UserDao를 설정하는 메서드
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    private DataSource dataSource; // 데이터베이스 연결을 위한 DataSource

    // DataSource를 설정하는 메서드
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    // 사용자 레벨 업그레이드를 처리하는 메서드
    public void upgradeLevels() throws Exception {
        // 트랜잭션 동기화 시작
        TransactionSynchronizationManager.initSynchronization();
        // 데이터베이스 연결 가져오기
        Connection c = DataSourceUtils.getConnection(dataSource); 
        // 자동 커밋 비활성화
        c.setAutoCommit(false);
        
        try {                                   
            // 모든 사용자 가져오기
            List<User> users = userDao.getAll();
            // 각 사용자에 대해 레벨 업그레이드 체크 및 수행
            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            // 모든 작업 성공 시 커밋
            c.commit();  
        } catch (Exception e) {    
            // 예외 발생 시 롤백
            c.rollback();
            throw e;
        } finally {
            // 데이터베이스 연결 해제
            DataSourceUtils.releaseConnection(c, dataSource);    
            // 트랜잭션 동기화 리소스 언바인드 및 클리어
            TransactionSynchronizationManager.unbindResource(this.dataSource);  
            TransactionSynchronizationManager.clearSynchronization();  
        }
    }
    
    // 사용자가 업그레이드 가능한지 확인하는 메서드
    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel(); 
        switch(currentLevel) {                                   
        case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER); 
        case SILVER: return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
        case GOLD: return false;
        default: throw new IllegalArgumentException("Unknown Level: " + currentLevel); 
        }
    }

    // 사용자의 레벨을 업그레이드하고 데이터베이스에 업데이트하는 메서드
    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
    
    // 사용자를 데이터베이스에 추가하는 메서드
    public void add(User user) {
        // 사용자 레벨이 null일 경우 BASIC으로 설정
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}