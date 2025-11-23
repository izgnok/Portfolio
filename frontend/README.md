# 🎨 Portfolio Frontend

React + Vite 기반 개인 포트폴리오 웹사이트입니다.  
Spring Boot 백엔드와 완벽하게 연동됩니다.

---

## 🚀 빠른 시작

### 1. 설치
```bash
npm install
```

### 2. 백엔드 실행 (필수)
```bash
# 백엔드 디렉토리에서
./mvnw spring-boot:run
```

### 3. 프론트엔드 실행
```bash
npm run dev
```

- **프론트엔드**: http://localhost:5173
- **백엔드 API**: http://localhost:8080/api

---

## ✨ 주요 기능

### 방문자 페이지
- **메인 페이지** - 프로필, 키워드, 학력, 자격증, 수상 프로젝트, 기술스택
- **프로젝트 목록** - 전체 프로젝트 표시, 수상 뱃지
- **프로젝트 상세** - 이미지 갤러리, 시스템 아키텍처, 상세 정보
- **자동 정렬**:
  - 교육 - 종료일 기준 최신순
  - 자격증 - 취득일 기준 최신순
  - 수상 프로젝트 - 종료일 기준 최신순
  - 기술스택 - Frontend → Backend → IoT → Database → CI/CD → Others 순

### 관리자 페이지
- **프로필 관리** - 이미지 업로드, **키워드 드래그 앤 드롭 순서 조정**
- **학력/자격증 관리** - CRUD 기능, 자동 정렬, 독립된 섹션으로 분리
- **프로젝트 관리** - 탭 기반 UI, 여러 이미지 업로드, **'+'버튼 개별 항목 추가**, 문제-해결 쌍 입력, **프로젝트 상태(진행중/완료)**, 수상 정보
- **기술스택 관리** - 카테고리별 관리, 레벨 조정, 고정된 카테고리 순서
- **영문 UI** - 모든 관리자 페이지 영문 인터페이스 제공
- **간편한 접근** - Recent Projects 제거, 핵심 관리 기능에 집중
- **직관적 입력** - 모든 배열 필드에 개별 항목 추가/삭제 UI 적용

---

## 🛠️ 기술 스택

- **React 18** - UI 라이브러리
- **React Router v6** - 라우팅
- **Vite** - 빌드 도구
- **Axios** - HTTP 클라이언트
- **CSS Modules** - 스타일링

---

## 🔐 인증

세션 기반 인증 (HttpSession, JSESSIONID)을 사용합니다.

```javascript
// withCredentials: true로 세션 쿠키 자동 전송
const api = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,
});
```

---

## 📦 빌드

```bash
npm run build
```

빌드 결과물: `dist/` 폴더

---

## 📚 문서

상세한 문서는 [DOCUMENTATION.md](DOCUMENTATION.md)를 참고하세요.

### 주요 문서 내용
- 백엔드 API 매칭
- 인증 시스템
- @RequestPart 파일 업로드
- Base64 이미지 처리
- JSON 필드 관리
- 배포 가이드
- 트러블슈팅

---

## 🎨 디자인

- **메인 컬러**: `#667eea → #764ba2` (그라데이션)
- **카드 디자인**: 흰색 배경, 둥근 모서리, 그림자, 통일된 스타일
- **이모지 아이콘**: 각 섹션마다 활용
- **애니메이션**: Hover 효과, Fade-in
- **드래그 앤 드롭**: 직관적인 순서 조정 UI (키워드 전용)
- **레벨 인디케이터**: 기술 수준 시각화 (5단계 바)
- **자동 정렬**: 날짜 기반 자동 정렬 (교육, 자격증, 프로젝트)
- **날짜 형식**: 모든 날짜 YYYY.MM.DD 형식으로 통일 (예: 2024.09.01)
- **버튼 표준화**: 
  - 모든 Edit/Delete 버튼 완전히 동일한 형태 (색상만 다름)
  - 사각형 디자인 (border-radius: 10px)
  - 단색 배경 (그라데이션 제거)
  - 통일된 크기 (padding: 8px 16px, font-size: 13px)
  - 일관된 hover 효과 (transform + box-shadow)
  - 카드 하단 우측 정렬
- **섹션 구분**: Education과 Certificates가 하나의 컨테이너에서 명확하게 구분

---

## 📁 프로젝트 구조

```
src/
├── components/      # 공통 컴포넌트
├── pages/           # 페이지 컴포넌트
│   └── admin/       # 관리자 페이지
├── services/        # API 서비스
└── App.jsx          # 라우팅
```

---

## 📄 라이선스

개인 포트폴리오용 프로젝트입니다.
