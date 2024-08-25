package com.jinsu.springframe.domain;

// User 클래스는 사용자 정보를 관리하는 도메인 객체입니다.
public class User {
	
	// 사용자 ID를 저장하는 필드입니다.
	String id;
	
	// 사용자의 이름을 저장하는 필드입니다.
	String name;
	
	// 사용자의 비밀번호를 저장하는 필드입니다.
	String password;
	
	// 사용자의 이메일 주소를 저장하는 필드입니다.
	String email;
	
	// 사용자의 현재 레벨을 나타내는 Level 열거형 타입의 필드입니다.
	Level level;
	
	// 사용자의 로그인 횟수를 저장하는 필드입니다.
	int login;
	
	// 사용자가 추천 받은 횟수를 저장하는 필드입니다.
	int recommend;
	
	// 기본 생성자입니다.
	public User() {
	}
	
	// 매개변수를 받는 생성자로, 모든 필드를 초기화합니다.
	public User(String id, String name, String password, String email,
			Level level, int login, int recommend) {
		super();  // 상위 클래스(Object)의 생성자를 호출합니다.
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
	}

	// 사용자 ID에 대한 getter 메서드입니다.
	public String getId() {
		return id;
	}

	// 사용자 ID에 대한 setter 메서드입니다.
	public void setId(String id) {
		this.id = id;
	}

	// 사용자 이름에 대한 getter 메서드입니다.
	public String getName() {
		return name;
	}

	// 사용자 이름에 대한 setter 메서드입니다.
	public void setName(String name) {
		this.name = name;
	}

	// 사용자 비밀번호에 대한 getter 메서드입니다.
	public String getPassword() {
		return password;
	}

	// 사용자 비밀번호에 대한 setter 메서드입니다.
	public void setPassword(String password) {
		this.password = password;
	}

	// 사용자 레벨에 대한 getter 메서드입니다.
	public Level getLevel() {
		return level;
	}

	// 사용자 레벨에 대한 setter 메서드입니다.
	public void setLevel(Level level) {
		this.level = level;
	}

	// 사용자 로그인 횟수에 대한 getter 메서드입니다.
	public int getLogin() {
		return login;
	}

	// 사용자 로그인 횟수에 대한 setter 메서드입니다.
	public void setLogin(int login) {
		this.login = login;
	}

	// 사용자 추천 횟수에 대한 getter 메서드입니다.
	public int getRecommend() {
		return recommend;
	}

	// 사용자 추천 횟수에 대한 setter 메서드입니다.
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	
	// 사용자 이메일에 대한 getter 메서드입니다.
	public String getEmail() {
		return email;
	}

	// 사용자 이메일에 대한 setter 메서드입니다.
	public void setEmail(String email) {
		this.email = email;
	}
	
	// 사용자 레벨을 업그레이드하는 메서드입니다.
	public void upgradeLevel() {
		// 다음 레벨을 가져옵니다.
		Level nextLevel = this.level.nextLevel();	
		
		// 다음 레벨이 존재하지 않는 경우 예외를 던집니다.
		if (nextLevel == null) { 								
			throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다");
		}
		else {
			// 다음 레벨이 존재하면 레벨을 업그레이드합니다.
			this.level = nextLevel;
		}	
	}

}