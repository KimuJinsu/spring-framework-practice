package com.jinsu.springframe.service;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.domain.Level;
import com.jinsu.springframe.domain.User;

// UserServiceImpl 클래스는 UserService 인터페이스를 구현하며,
// 사용자 레벨 관리 및 이메일 전송 기능을 제공합니다.
public class UserServiceImpl implements UserService {

    // 사용자 레벨 업그레이드 기준 상수
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;  // 실버 레벨로 업그레이드하기 위한 최소 로그인 횟수
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;   // 골드 레벨로 업그레이드하기 위한 최소 추천 횟수

    private UserDao userDao;           // UserDao 객체를 통해 데이터베이스 작업을 수행
    private MailSender mailSender;     // MailSender 객체를 통해 이메일을 전송

    // UserDao를 주입하기 위한 setter 메서드
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    // MailSender를 주입하기 위한 setter 메서드
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 사용자를 추가하는 메서드
    // 사용자 레벨이 null일 경우 기본적으로 BASIC 레벨로 설정
    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    // 사용자들의 레벨을 업그레이드하는 메서드
    public void upgradeLevels() {
        List<User> users = userDao.getAll();  // 모든 사용자 목록을 가져옴
        for (User user : users) {
            if (canUpgradeLevel(user)) {      // 각 사용자가 업그레이드 가능한지 확인
                upgradeLevel(user);           // 업그레이드 가능하면 레벨을 업그레이드
            }
        }
    }

    // 특정 사용자가 레벨 업그레이드가 가능한지 확인하는 메서드
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();  // 사용자의 현재 레벨을 가져옴
        switch(currentLevel) {
        case BASIC: 
            return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);  // BASIC -> SILVER 업그레이드 조건 확인
        case SILVER: 
            return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD); // SILVER -> GOLD 업그레이드 조건 확인
        case GOLD: 
            return false;  // GOLD는 최고 레벨이므로 더 이상 업그레이드 불가
        default: 
            throw new IllegalArgumentException("Unknown Level: " + currentLevel); 
        }
    }

    // 사용자의 레벨을 업그레이드하는 메서드
    public void upgradeLevel(User user) {
        user.upgradeLevel();           // 사용자 객체의 레벨을 업그레이드
        userDao.update(user);          // 업그레이드된 정보를 데이터베이스에 저장
        sendUpgradeEMail(user);        // 업그레이드 성공 이메일 전송
    }

    // 사용자에게 레벨 업그레이드 완료 이메일을 보내는 메서드
    private void sendUpgradeEMail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());  // 수신자 설정
        mailMessage.setFrom("useradmin@ksug.org");  // 발신자 설정
        mailMessage.setSubject("Upgrade 반가워요");  // 이메일 제목 설정
        mailMessage.setText("등급을 축하 드려요. " + user.getLevel().name());  // 이메일 내용 설정
        
        this.mailSender.send(mailMessage);  // 이메일 전송
    }

}