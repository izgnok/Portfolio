# Backend - Spring Boot REST API

## 📌 개요
포트폴리오 프로젝트의 백엔드 API 서버입니다.
Spring Boot 3.2.0 기반으로 RESTful API를 제공하며, JWT 인증을 통한 보안이 적용되어 있습니다.

## 🛠 기술 스택
- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Security**
- **Spring Data JPA**: ORM
- **MySQL**: 8.0
- **Maven**: 빌드 도구
- **Docker**: 컨테이너화

## 📂 프로젝트 구조
```
Backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/portfolio/
│   │   │       ├── config/          # 설정 클래스
│   │   │       ├── controller/      # REST 컨트롤러
│   │   │       ├── service/         # 비즈니스 로직
│   │   │       ├── repository/      # JPA 리포지토리
│   │   │       ├── entity/          # JPA 엔티티
│   │   │       ├── dto/             # 데이터 전송 객체
│   │   │       ├── security/        # 보안 설정
│   │   │       └── util/            # 유틸리티
│   │   └── resources/
│   │       ├── application.yml      # 애플리케이션 설정
│   │       └── application-prod.yml # 운영 환경 설정
│   └── test/                        # 테스트 코드
├── Dockerfile
├── pom.xml
└── README.md
```

## 🚀 실행 방법

### 로컬 환경
```bash
# 의존성 설치 및 빌드
./mvnw clean install

# 애플리케이션 실행
./mvnw spring-boot:run

# 또는 JAR 파일로 실행
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Docker 환경
```bash
# Docker 이미지 빌드
docker build -t portfolio-backend .

# 컨테이너 실행
docker run -p 8080:8080 portfolio-backend
```

## ⚙️ 환경 설정

### application.yml
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/portfolio_db
    username: root
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000  # 24시간
```

### 환경 변수
다음 환경 변수를 설정해야 합니다:
```bash
export DB_PASSWORD=your_database_password
export JWT_SECRET=your_jwt_secret_key
```

## 📡 API 엔드포인트

### 인증 (Authentication)
```
POST   /api/auth/signup    # 회원가입
POST   /api/auth/login     # 로그인
POST   /api/auth/refresh   # 토큰 갱신
```

### 사용자 (Users)
```
GET    /api/users          # 전체 사용자 조회 (관리자)
GET    /api/users/{id}     # 특정 사용자 조회
PUT    /api/users/{id}     # 사용자 정보 수정
DELETE /api/users/{id}     # 사용자 삭제
```

### 프로필 (Profile)
```
GET    /api/profile        # 프로필 조회
POST   /api/profile        # 프로필 생성
PUT    /api/profile        # 프로필 수정
```

### 자격증 (Certificate)
```
GET    /api/certificates           # 전체 자격증 조회
GET    /api/certificates/{id}      # 특정 자격증 조회
POST   /api/certificates           # 자격증 추가
PUT    /api/certificates/{id}      # 자격증 수정
DELETE /api/certificates/{id}      # 자격증 삭제
```

### 키워드 (Keyword)
```
GET    /api/keywords               # 전체 키워드 조회
GET    /api/keywords/search?q=     # 키워드 검색
POST   /api/keywords               # 키워드 추가
DELETE /api/keywords/{id}          # 키워드 삭제
```

### 파일 업로드
```
POST   /api/upload         # 파일 업로드 (최대 10MB)
```

## 🗄 데이터베이스 스키마

### Users 테이블
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
);
```

### Certificate 테이블
```sql
CREATE TABLE certificate (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    issuer VARCHAR(100),
    issue_date DATE,
    certificate_number VARCHAR(100),  -- 자격증 번호
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### Keyword 테이블
```sql
CREATE TABLE keyword (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,  -- 길이 50 → 200으로 증가
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Profile 테이블
```sql
CREATE TABLE profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bio TEXT,
    skills TEXT,
    experience TEXT,
    education TEXT,
    user_id BIGINT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## 🔒 보안


### Spring Security 설정
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // JWT 필터 체인 설정
    // CORS 설정
    // 권한 설정 (USER, ADMIN)
}
```

### 비밀번호 암호화
- BCrypt 해싱 알고리즘 사용
- Salt 자동 생성

## 📦 빌드 & 배포

### Maven 빌드
```bash
./mvnw clean package -DskipTests
```

### Docker 이미지 빌드
```bash
docker build -t izgnok/portfolio-backend:latest .
docker push izgnok/portfolio-backend:latest
```

### GitHub Actions CI/CD
- `main` 브랜치 push 시 자동 배포
- Docker Hub에 이미지 푸시
- EC2 서버에서 자동 pull & 재시작

## 🐛 트러블슈팅

### 데이터베이스 연결 실패
**증상**: `Connection refused` 에러
**해결**:
```bash
# MySQL 컨테이너 실행 확인
docker ps | grep mysql

# 네트워크 연결 확인
docker network inspect portfolio_default
```

### JPA 스키마 자동 생성 오류
**증상**: 테이블 생성 실패
**해결**:
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update  # 또는 create, validate
```

### JWT 토큰 만료
**증상**: 401 Unauthorized
**해결**: `/api/auth/refresh`로 토큰 갱신

### 파일 업로드 실패
**증상**: 413 Request Entity Too Large
**해결**: Nginx 설정 확인
```nginx
client_max_body_size 10M;
```

## 🧪 테스트

### 단위 테스트 실행
```bash
./mvnw test
```

### API 테스트 (Postman/cURL)
```bash
# 로그인
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"password"}'

# 사용자 조회 (JWT 토큰 필요)
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer <your_token>"
```

## 📊 모니터링

### 로그 확인
```bash
# Docker 로그
docker logs -f portfolio-backend

# 애플리케이션 로그
tail -f logs/application.log
```

### 헬스 체크
```bash
curl http://localhost:8080/actuator/health
```

## 🔧 의존성

주요 라이브러리:
```xml
<dependencies>
    <!-- Spring Boot Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- MySQL Driver -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
    
    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
</dependencies>
```

## 📝 개발 가이드

### 새로운 API 추가
1. Entity 정의 (`entity/`)
2. Repository 생성 (`repository/`)
3. Service 구현 (`service/`)
4. Controller 작성 (`controller/`)
5. DTO 정의 (`dto/`)

### 코드 스타일
- Google Java Style Guide 준수
- 들여쓰기: 4 spaces
- 패키지명: 소문자
- 클래스명: PascalCase
- 메소드명: camelCase

## 🔗 관련 링크
- [프로젝트 루트 README](../README.md)
- [Docker Hub Repository](https://hub.docker.com/r/izgnok/portfolio-backend)
- [GitHub Repository](https://github.com/izgnok/Portfolio)

## 📄 라이선스
이 프로젝트는 개인 포트폴리오 목적으로 제작되었습니다.
