package com.jinsu.springframe.service;

import java.util.Properties;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// MailSenderCVImpl 클래스는 JavaMailSenderImpl 클래스를 확장하여 이메일 전송 기능을 설정하는 클래스입니다.
// 주어진 Properties 객체를 사용하여 추가적인 이메일 설정을 적용할 수 있습니다.
public class MailSenderCVImpl extends JavaMailSenderImpl {
    
    // Properties 객체를 저장하기 위한 인스턴스 변수
    Properties props;
    
    // Properties 객체를 설정하는 메서드
    public void setProps(Properties props) {
        this.props = props; // 전달받은 Properties 객체를 인스턴스 변수에 저장
        settingProperites(); // 설정 메서드를 호출하여 추가 설정을 적용
    }
    
    // Properties 객체를 설정하는 메서드
    private void settingProperites() {
        this.setJavaMailProperties(props); // JavaMailSenderImpl의 setJavaMailProperties 메서드를 호출하여 설정 적용
    }
}