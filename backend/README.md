# 포트폴리오 백엔드 API 서버

Spring Boot 기반의 포트폴리오 관리 시스템 백엔드 API

## 📋 프로젝트 개요

개인 포트폴리오 웹사이트의 백엔드 API 서버입니다.
관리자 페이지에서 프로필, 프로젝트, 학력, 자격증, 스킬 등을 CRUD 관리할 수 있으며,
방문자 페이지에서는 공개 API를 통해 데이터를 조회할 수 있습니다.

## 🛠️ 기술 스택

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security** (세션 기반 인증)
- **MySQL 8.0**

### Build Tool
- **Gradle**

### 라이브러리
- **Lombok** - 보일러플레이트 코드 제거
- **Validation** - 입력값 검증
- **BCrypt** - 비밀번호 암호화

## 📁 프로젝트 구조

```
src/main/java/kong/portfolio/
├── config/
│   └── SecurityConfig.java           # Spring Security 설정
├── common/
│   ├── response/
│   │   └── ResponseDto.java          # 공통 응답 DTO
│   └── status/
│       └── StatusCode.java           # 응답 상태 코드
└── portfolio/
    ├── entity/                       # Entity (8개)
    │   ├── User.java
    │   ├── Profile.java
    │   ├── Keyword.java
    │   ├── Education.java
    │   ├── Certificate.java
    │   ├── Skill.java
    │   ├── Project.java
    │   └── ProjectImage.java
    ├── repository/                   # Repository (8개)
    ├── application/                  # Service (8개)
    ├── presentation/                 # Controller (7개)
    └── dto/                          # Request/Response DTO
```

## 🗄️ 데이터베이스 설계

### Entity 목록 (8개)

1. **User** - 관리자 계정
2. **Profile** - 프로필 (1개만 존재)
3. **Keyword** - 키워드
4. **Education** - 학력
5. **Certificate** - 자격증
6. **Skill** - 스킬 (카테고리별)
7. **Project** - 프로젝트 (JSON 필드 활용)
8. **ProjectImage** - 프로젝트 이미지 (BLOB 저장)

### 주요 특징

- **이미지 저장**: MySQL BLOB으로 직접 저장 (프로필 이미지, 프로젝트 이미지, 아키텍처 이미지)
- **JSON 활용**: 프로젝트의 요약, 기술스택, 성과 등을 JSON 컬럼으로 저장
- **세션 인증**: Spring Security 세션 기반 인증 (JWT 미사용)

## 🌐 API 엔드포인트

### 인증 API
| Method | URL | 설명 | 권한 |
|--------|-----|------|------|
| POST | `/api/users/login` | 로그인 | 공개 |
| POST | `/api/users/logout` | 로그아웃 | 공개 |

### 프로필 API
| Method | URL | 설명 | 권한 |
|--------|-----|------|------|
| GET | `/api/profile` | 프로필 조회 | 공개 |
| POST | `/api/profile` | 프로필 저장/수정 (이미지 포함) | 관리자 |

### 키워드 API
| Method | URL | 설명 | 권한 |
|--------|-----|------|------|
| GET | `/api/keywords` | 키워드 목록 조회 | 공개 |
| POST | `/api/keywords` | 키워드 추가 | 관리자 |
| PUT | `/api/keywords/order` | 키워드 순서 변경 | 관리자 |
| DELETE | `/api/keywords/{id}` | 키워드 삭제 | 관리자 |

### 학력 API
| Method | URL | 설명 | 권한 |
|--------|-----|------|------|
| GET | `/api/educations` | 학력 목록 조회 | 공개 |
| POST | `/api/educations` | 학력 추가 | 관리자 |
| PUT | `/api/educations/{id}` | 학력 수정 | 관리자 |
| DELETE | `/api/educations/{id}` | 학력 삭제 | 관리자 |

### 자격증 API
| Method | URL | 설명 | 권한 |
|--------|-----|------|------|
| GET | `/api/certificates` | 자격증 목록 조회 | 공개 |
| POST | `/api/certificates` | 자격증 추가 | 관리자 |
| PUT | `/api/certificates/{id}` | 자격증 수정 | 관리자 |
| DELETE | `/api/certificates/{id}` | 자격증 삭제 | 관리자 |

### 스킬 API
| Method | URL | 설명 | 권한 |
|--------|-----|------|------|
| GET | `/api/skills` | 스킬 목록 조회 | 공개 |
| POST | `/api/skills` | 스킬 추가 | 관리자 |
| PUT | `/api/skills/{id}` | 스킬 수정 | 관리자 |
| DELETE | `/api/skills/{id}` | 스킬 삭제 | 관리자 |

### 프로젝트 API
| Method | URL | 설명 | 권한 |
|--------|-----|------|------|
| GET | `/api/projects` | 프로젝트 목록 조회 | 공개 |
| GET | `/api/projects/awarded` | 수상작만 조회 | 공개 |
| GET | `/api/projects/{id}` | 프로젝트 상세 조회 | 공개 |
| POST | `/api/projects` | 프로젝트 추가 (이미지 포함) | 관리자 |
| PUT | `/api/projects/{id}` | 프로젝트 수정 (이미지 포함) | 관리자 |
| DELETE | `/api/projects/{id}` | 프로젝트 삭제 | 관리자 |

**총 28개 API** (공개 12개 / 관리자 전용 16개)

## 🔐 보안 설정

### Spring Security
- **공개 API**: 모든 GET 요청 (조회 기능)
- **관리자 전용**: POST, PUT, DELETE 요청
- **인증 방식**: 세션 기반 (JSESSIONID 쿠키)
- **비밀번호 암호화**: BCrypt

## 🚀 실행 방법

### 1. 사전 요구사항
- Java 17 이상
- MySQL 8.0 이상
- Gradle 7.x 이상

### 2. 데이터베이스 설정

**MySQL 데이터베이스 생성:**
```sql
CREATE DATABASE portfolio CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**application.yml 설정:**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/portfolio?serverTimezone=Asia/Seoul
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 3. 애플리케이션 실행

```bash
# Gradle 빌드
./gradlew build

# 실행
./gradlew bootRun
```

서버 실행 후: `http://localhost:8080`

### 4. 초기 관리자 계정 생성

```java
// UserService의 createAdmin 메서드 활용
// 또는 직접 SQL 실행
INSERT INTO users (username, password, role) 
VALUES ('admin', '$2a$10$암호화된비밀번호', 'ADMIN');
```

## 📝 API 사용 예시

### 로그인
```bash
POST /api/users/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

### 프로필 조회 (공개)
```bash
GET /api/profile
```

### 프로젝트 추가 (관리자)
```bash
POST /api/projects
Cookie: JSESSIONID=xxx
Content-Type: multipart/form-data

project: {
  "name": "포트폴리오 웹사이트",
  "teamSize": 1,
  "startDate": "2024-01-01",
  "endDate": "2024-03-31",
  "status": "완료",
  "hasAward": true,
  "awardName": "우수상",
  "summaries": "[\"요약1\", \"요약2\"]",
  "techBackend": "[\"Spring Boot\", \"MySQL\"]"
}
projectImages: [파일1, 파일2]
architectureImage: 파일
```

## 🧪 테스트

```bash
# 전체 테스트 실행
./gradlew test

# 특정 테스트 실행
./gradlew test --tests "ClassName"
```

## 📦 배포

### AWS EC2 배포 (예정)
```bash
# JAR 파일 생성
./gradlew bootJar

# 실행
java -jar build/libs/portfolio-0.0.1-SNAPSHOT.jar
```

## 🔧 개발 환경 설정

### IntelliJ IDEA
1. Lombok 플러그인 설치
2. Enable annotation processing 활성화
3. Java 17 SDK 설정

### VS Code
1. Extension Pack for Java 설치
2. Spring Boot Extension Pack 설치

## 📌 주요 기능

### 1. 이미지 관리
- MySQL BLOB으로 이미지 저장
- Base64 인코딩하여 JSON 응답
- multipart/form-data로 업로드

### 2. JSON 활용
- 프로젝트 요약, 기술스택 등을 JSON 컬럼으로 저장
- 프론트엔드에서 JSON.stringify/parse로 처리

### 3. 순서 관리
- displayOrder 필드로 항목 순서 관리
- 드래그 앤 드롭 순서 변경 지원

## 🐛 트러블슈팅

### 이미지 크기 제한
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 20MB
```

### CORS 에러
SecurityConfig에서 CORS 설정 확인

### 세션 유지
JSESSIONID 쿠키 확인

## 👥 기여

이 프로젝트는 개인 포트폴리오 프로젝트입니다.

## 📄 라이선스

MIT License

---