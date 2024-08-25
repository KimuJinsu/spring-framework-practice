#### Spring Framework에서 데이터베이스와의 상호작용을 효율적으로 처리하기 위해 다양한 기술과 모듈이 제공됩니다. Spring은 데이터베이스 작업을 단순화하고 코드의 유지보수를 용이하게 하기 위해 여러 가지 기능을 제공합니다. 아래는 Spring Framework에서 데이터베이스와 관련된 주요 개념과 기술을 설명합니다.

# 1. Spring JDBC

Spring JDBC는 SQL 데이터베이스와의 상호작용을 단순화하는 모듈입니다. 직접적인 JDBC API의 사용을 줄이고, 데이터베이스 작업을 쉽게 처리할 수 있도록 도와줍니다.

	•	JdbcTemplate: 데이터베이스 작업을 간소화하는 핵심 클래스입니다. SQL 쿼리를 실행하고 결과를 매핑하는 작업을 쉽게 수행할 수 있습니다.
	•	NamedParameterJdbcTemplate: SQL 쿼리에서 매개변수를 명명하여 가독성을 높이고 코드의 안전성을 강화합니다.

 # 2. Spring Data JPA

 Spring Data JPA는 Java Persistence API (JPA)를 사용하여 데이터베이스 작업을 단순화하는 모듈입니다. JPA는 객체-관계 매핑(ORM)을 지원하여 자바 객체와 데이터베이스 테이블 간의 매핑을 관리합니다.

	•	Entity: 데이터베이스 테이블에 매핑되는 클래스입니다.
	•	Repository: 데이터베이스 작업을 위한 인터페이스입니다. JpaRepository를 상속받아 CRUD 작업을 자동으로 제공받을 수 있습니다.

 # 3. Spring Data JDBC

 Spring Data JDBC는 Spring Data JPA의 간단한 대안으로, 더 적은 설정과 코드로 데이터베이스 작업을 수행할 수 있게 합니다. 객체-관계 매핑을 최소화하고, SQL을 직접 작성하여 사용합니다.

	•	JdbcAggregateTemplate: 객체를 데이터베이스에 저장하거나 조회하는 데 사용됩니다.

 # 4. Spring Transactions

 Spring에서는 데이터베이스 트랜잭션 관리를 제공하여 일관성과 원자성을 보장합니다.

	•	@Transactional: 메서드나 클래스에 이 어노테이션을 추가하여 트랜잭션을 관리합니다. 트랜잭션이 성공적으로 완료되면 커밋되고, 예외가 발생하면 롤백됩니다.

 # 5. Spring Boot와 데이터베이스 설정

 Spring Boot는 데이터베이스와의 통합을 더 쉽게 만들어줍니다. application.properties 또는 application.yml 파일에서 데이터베이스 연결 및 JPA 설정을 간편하게 구성할 수 있습니다.

 # 6. Spring Data MongoDB

 MongoDB와 같은 NoSQL 데이터베이스와의 통합을 위한 Spring Data 모듈입니다. 문서 기반의 데이터 저장소와 상호작용할 수 있습니다.

 ### 이와 같은 다양한 Spring 모듈과 기술을 활용하면 데이터베이스와의 상호작용을 효율적으로 관리할 수 있습니다. 각 모듈의 특징을 잘 이해하고 적절하게 선택하여 사용하는 것이 중요합니다.



