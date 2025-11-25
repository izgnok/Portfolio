# Portfolio Project

## 📌 프로젝트 개요
Spring Boot + React를 활용한 개인 포트폴리오 웹사이트입니다.
AWS EC2에 Docker 컨테이너로 배포되며, GitHub Actions를 통한 자동 CI/CD 파이프라인을 구축했습니다.

## 🌐 배포 정보
- **도메인**: https://izgnok.site
- **서버**: AWS EC2 (Ubuntu 22.04, t3.small)

## 🛠 기술 스택

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- MySQL 8.0

### Frontend
- React 18
- Vite
- React Router
- Axios

### Infrastructure
- AWS EC2
- Docker & Docker Compose
- Nginx (Reverse Proxy)
- GitHub Actions (CI/CD)
- Docker Hub (Container Registry)

## 📂 프로젝트 구조
```
Portfolio/
├── Backend/              # Spring Boot 백엔드
├── Frontend/             # React 프론트엔드
├── docker-compose.yml    # Docker 컨테이너 설정
├── .github/
│   └── workflows/
│       └── deploy.yml    # GitHub Actions CI/CD
└── README.md
```

## 🚀 로컬 실행 방법

### 1. 사전 요구사항
- Java 17
- Node.js 18+
- MySQL 8.0
- Docker & Docker Compose

### 2. Backend 실행
```bash
cd Backend
./mvnw spring-boot:run
```

### 3. Frontend 실행
```bash
cd Frontend
npm install
npm run dev
```

## 🐳 Docker 배포

### 전체 서비스 시작
```bash
docker compose up -d
```

### 서비스 중지
```bash
docker compose down
```

### 로그 확인
```bash
docker compose logs -f
```

## 🔄 CI/CD 파이프라인

GitHub Actions를 통한 자동 배포:

1. **트리거**: `main` 브랜치에 push 또는 PR merge
2. **빌드**: Backend Maven 빌드 → Docker 이미지 생성
3. **배포**: Docker Hub에 이미지 푸시 → EC2에서 자동 pull & 재시작

### 주요 워크플로우
- `.github/workflows/deploy.yml`
- Docker Hub 자동 푸시
- EC2 자동 배포 (SSH를 통한 원격 실행)

## 🗄 데이터베이스

### MySQL 정보
- **버전**: 8.0
- **포트**: 3306
- **데이터베이스**: portfolio_db

### 주요 테이블
- `users` - 사용자 정보
- `certificate` - 자격증 정보
- `keyword` - 키워드 정보
- `profile` - 프로필 정보

### 백업 시스템
- **자동 백업**: 매일 00:00 (Cron)
- **암호화**: AES-256-CBC
- **보관 기간**: 7일
- **백업 위치**: `/home/ubuntu/backup/mysql/`

백업/복원 상세 가이드: [MySQL백업복원_사용가이드.txt](./MySQL백업복원_사용가이드.txt)

## 🔒 보안 설정

### Nginx 설정
- HTTPS 리다이렉션
- 파일 업로드 제한: 10MB
- Reverse Proxy (Backend API)

### 환경 변수
민감한 정보는 환경 변수로 관리:
```bash
DB_PASSWORD=<MySQL 비밀번호>
JWT_SECRET=<JWT 시크릿 키>
```

## 📝 주요 기능
- 사용자 인증/인가 (JWT)
- 프로필 관리
- 자격증 정보 관리
- 키워드 검색
- 파일 업로드

## 🐛 트러블슈팅

### 파일 업로드 413 에러
**원인**: Nginx 기본 업로드 크기 제한 (1MB)
**해결**: `/etc/nginx/nginx.conf`에 `client_max_body_size 10M;` 추가

### Docker 컨테이너 재시작 안됨
**원인**: 구버전 docker-compose 명령어 사용
**해결**: `docker-compose` → `docker compose` (v2 명령어)

### MySQL 접속 실패
**원인**: 잘못된 IP 또는 방화벽 설정
**해결**: 
- EC2 보안 그룹에서 3306 포트 허용
- SSH 터널링 사용 시 정확한 IP 확인 (15.164.32.24)

## 📚 참고 문서
- [Backend README](./Backend/README.md)
- [MySQL 백업/복원 가이드](./MySQL백업복원_사용가이드.txt)

## 👤 개발자
- GitHub: https://github.com/izgnok
- Portfolio: https://izgnok.site

## 📄 라이선스
이 프로젝트는 개인 포트폴리오 목적으로 제작되었습니다.
