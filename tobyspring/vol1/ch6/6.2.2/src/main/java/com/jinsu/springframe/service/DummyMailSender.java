package com.jinsu.springframe.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

// DummyMailSender 클래스는 MailSender 인터페이스의 구현체로, 테스트 용도로 사용됩니다.
// 실제 이메일 전송 기능을 수행하지 않고, 메서드 호출을 흉내내기만 합니다.
public class DummyMailSender implements MailSender {

    // 이 메서드는 단일 SimpleMailMessage 객체를 받아야 하지만,
    // DummyMailSender에서는 아무 작업도 하지 않습니다.
    @Override
    public void send(SimpleMailMessage mailMessage) throws MailException {
        // 아무 동작도 하지 않음 (실제 이메일 전송 코드가 들어가는 자리)
    }

    // 이 메서드는 SimpleMailMessage 배열을 받아야 하지만,
    // DummyMailSender에서는 아무 작업도 하지 않습니다.
    @Override
    public void send(SimpleMailMessage[] mailMessage) throws MailException {
        // 아무 동작도 하지 않음 (실제 이메일 전송 코드가 들어가는 자리)
    }
}