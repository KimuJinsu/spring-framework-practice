package com.jinsu.springframe.domain;

public class User {
    
    // 사용자 정보 필드
    String id;          // 사용자 ID
    String name;        // 사용자 이름
    String password;    // 사용자 비밀번호
    Level level;        // 사용자 레벨
    int login;          // 로그인 횟수
    int recommend;      // 추천 수
    
    // 기본 생성자
    public User() {
    }
    
    // 모든 필드를 초기화하는 생성자
    public User(String id, String name, String password, Level level,
                int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    // 사용자 ID 반환
    public String getId() {
        return id;
    }
    
    // 사용자 ID 설정
    public void setId(String id) {
        this.id = id;
    }
    
    // 사용자 이름 반환
    public String getName() {
        return name;
    }
    
    // 사용자 이름 설정
    public void setName(String name) {
        this.name = name;
    }
    
    // 사용자 비밀번호 반환
    public String getPassword() {
        return password;
    }
    
    // 사용자 비밀번호 설정
    public void setPassword(String password) {
        this.password = password;
    }

    // 사용자 레벨 반환
    public Level getLevel() {
        return level;
    }

    // 사용자 레벨 설정
    public void setLevel(Level level) {
        this.level = level;
    }

    // 로그인 횟수 반환
    public int getLogin() {
        return login;
    }

    // 로그인 횟수 설정
    public void setLogin(int login) {
        this.login = login;
    }

    // 추천 수 반환
    public int getRecommend() {
        return recommend;
    }

    // 추천 수 설정
    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
    
    // 사용자 레벨을 업그레이드하는 메서드
    public void upgradeLevel() {
        // 현재 레벨의 다음 레벨을 찾기
        Level nextLevel = this.level.nextLevel();    
        
        // 다음 레벨이 null이면 업그레이드 불가능
        if (nextLevel == null) {                                
            throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다");
        }
        // 다음 레벨로 업데이트
        else {
            this.level = nextLevel;
        }    
    }
}