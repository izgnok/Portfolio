# 🎨 Portfolio Frontend

React + Vite 기반 개인 포트폴리오 웹사이트 프론트엔드입니다.  
Spring Boot 백엔드와 완벽하게 연동되며, 세션 기반 인증을 사용합니다.

---

## 🚀 빠른 시작

### 1. 설치
```bash
npm install
```

### 2. 환경 변수 설정
```bash
# .env 파일 생성
VITE_API_URL=http://localhost:8080/api
```

### 3. 백엔드 실행 (필수)
```bash
# 백엔드 디렉토리에서
./mvnw spring-boot:run
```

### 4. 프론트엔드 실행
```bash
npm run dev
```

- **프론트엔드**: http://localhost:5173
- **백엔드 API**: http://localhost:8080/api

---

## ✨ 주요 기능

### 📱 방문자 페이지
- **메인 페이지**
  - 프로필 정보 (이름, 생년월일, 연락처, GitHub)
  - 키워드 (디스플레이 순서대로 표시)
  - 학력 (종료일 기준 최신순 자동 정렬)
  - 자격증 (취득일 기준 최신순 자동 정렬, 자격번호 표시)
  - 수상 프로젝트 (종료일 기준 최신순)
  - 기술스택 (Frontend → Backend → IoT → Database → CI/CD → Others 순)

- **프로젝트 목록**
  - 전체 프로젝트 카드 표시
  - 수상 프로젝트 뱃지 표시 (🏆)
  - 프로젝트 상태 표시 (진행중/완료)

- **프로젝트 상세**
  - 프로젝트 이미지 갤러리 (좌우 네비게이션)
  - 시스템 아키텍처 이미지
  - 프로젝트 요약, 핵심 가치, 주요 기능
  - 나의 역할, 문제해결 (문제-해결 쌍)
  - 아쉬운 점, 개선 방안
  - 기술스택 (카테고리별 분류)
  - GitHub URL 링크

### 🔧 관리자 페이지
- **Control Panel**
  - 통계 카드 (프로젝트 수, 기술스택 수)
  - 핵심 관리 기능으로 바로가기

- **Profile 관리**
  - 프로필 이미지 업로드 및 미리보기
  - 기본 정보 수정 (이름, 생년월일, 연락처, GitHub)
  - 키워드 관리 (드래그 앤 드롭으로 순서 조정, 자동 순서 할당)

- **Education 관리**
  - CRUD 기능
  - 학교명, 기간, 상태, GPA 관리
  - 카드 간격 및 가독성 최적화

- **Certificates 관리**
  - CRUD 기능
  - 자격증명, 발급기관, 취득일, 자격번호 관리
  - 자동 정렬 (취득일 기준 최신순)
  - Edit/Delete 버튼 가운데 정렬

- **Projects 관리**
  - 탭 기반 UI (기본 정보 / 상세 내용 / 기술스택 / 이미지)
  - 프로젝트 이미지 다중 업로드
  - 아키텍처 이미지 업로드
  - 배열 필드마다 '+' 버튼으로 개별 항목 추가
  - 문제-해결 쌍 입력 UI
  - 프로젝트 상태 선택 (진행중/완료)
  - 수상 정보 입력 (수상 여부, 수상명, 수상 기관)
  - GitHub URL 관리

- **Skills 관리**
  - 카테고리별 관리 (Frontend / Backend / IoT / Database / CI/CD / Others)
  - 기술 레벨 조정 (1~5)
  - 고정된 카테고리 순서

- **공통**
  - 모든 관리자 페이지 영문 UI
  - 통일된 Edit/Delete 버튼 스타일
  - 실시간 메시지 피드백
  - 세션 기반 인증

---

## 🛠️ 기술 스택

### Core
- **React 18.3** - UI 라이브러리
- **React Router v6** - 클라이언트 사이드 라우팅
- **Vite 5.4** - 빌드 도구 및 개발 서버

### Libraries
- **Axios** - HTTP 클라이언트 (세션 쿠키 자동 포함)
- **React Hook Form** - 폼 관리 및 유효성 검사
- **CSS3** - 스타일링 (CSS Modules 없이 일반 CSS 사용)

### Features
- **Session-based Authentication** - HttpSession, JSESSIONID
- **FormData API** - 파일 업로드 (`multipart/form-data`)
- **Base64 Image Handling** - 백엔드로부터 Base64 이미지 수신
- **JSON Field Management** - 배열 필드를 JSON 문자열로 변환

---

## 🔐 인증 시스템

세션 기반 인증을 사용합니다.

```javascript
// Axios 인스턴스 설정
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true, // 세션 쿠키 자동 포함
});
```

### 로그인 플로우
1. 사용자가 로그인 모달에서 username, password 입력
2. `POST /api/users/login` 호출
3. 백엔드에서 세션 생성 및 JSESSIONID 쿠키 반환
4. 프론트엔드에서 localStorage에 관리자 상태 저장
5. 이후 모든 API 요청에 세션 쿠키 자동 포함

### 로그아웃 플로우
1. `POST /api/users/logout` 호출
2. 백엔드에서 세션 무효화
3. localStorage 정리
4. 메인 페이지로 리다이렉트

---

## 📡 API 연동

### API 서비스 구조
```javascript
// src/services/api.js
export const profileAPI = {
  get: () => api.get('/profile'),
  save: async (profileData, profileImage) => { /* FormData 처리 */ }
};

export const projectsAPI = {
  getAll: () => api.get('/projects'),
  getById: (id) => api.get(`/projects/${id}`),
  getAwarded: () => api.get('/projects/awarded'),
  create: async (projectData, projectImages, architectureImage) => { /* ... */ },
  update: async (id, projectData, projectImages, architectureImage) => { /* ... */ },
  delete: (id) => api.delete(`/projects/${id}`)
};

// educationsAPI, certificatesAPI, skillsAPI, keywordsAPI 등...
```

### 파일 업로드 방식
백엔드의 `@RequestPart` 어노테이션과 매칭:

```javascript
const formData = new FormData();

// JSON 데이터를 Blob으로 추가
const projectBlob = new Blob([JSON.stringify(projectData)], {
  type: 'application/json'
});
formData.append('project', projectBlob);

// 이미지 파일들 추가
projectImages.forEach(image => {
  formData.append('projectImages', image);
});

// 아키텍처 이미지 추가
if (architectureImage) {
  formData.append('architectureImage', architectureImage);
}

// Content-Type: multipart/form-data
await api.post('/projects', formData, {
  headers: { 'Content-Type': 'multipart/form-data' }
});
```

### 이미지 수정 시 주의사항
```javascript
// 이미지를 수정하지 않을 때는 undefined 전송
await projectsAPI.update(
  projectId,
  projectData,
  projectImageFiles.length > 0 ? projectImageFiles : undefined,  // 기존 이미지 유지
  architectureImageFile || undefined
);
```

### JSON 필드 처리
```javascript
// 백엔드로 전송 시
const projectData = {
  name: formData.title,
  summaries: JSON.stringify(formData.summaries),  // 배열 → JSON 문자열
  coreValues: JSON.stringify(formData.coreValues),
  // ...
};

// 백엔드로부터 수신 시
const parseJsonField = (field) => {
  if (!field) return [];
  if (typeof field === 'string') {
    try {
      return JSON.parse(field);
    } catch (e) {
      return [];
    }
  }
  return Array.isArray(field) ? field : [];
};
```

---

## 🎨 디자인 시스템

### 컬러 팔레트
- **메인 컬러**: `#667eea` → `#764ba2` (그라데이션)
- **성공**: `#28a745`
- **에러**: `#dc3545`
- **경고**: `#f59e0b`
- **배경**: `linear-gradient(135deg, #667eea 0%, #764ba2 100%)`

### 카드 디자인
```css
.card {
  background: white;
  border-radius: 20px;
  padding: 30px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}
```

### 버튼 표준화
모든 Edit/Delete 버튼은 완전히 동일한 형태:

```css
.btn-edit,
.btn-delete {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 10px 24px;
  height: 40px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 85px;
}

.btn-edit {
  background: #667eea;
  color: white;
}

.btn-delete {
  background: #dc3545;
  color: white;
}
```

### 애니메이션
```css
.fade-in {
  animation: fadeIn 0.6s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
```

### 날짜 형식
모든 날짜는 `YYYY.MM.DD` 형식으로 통일:

```javascript
export const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}.${month}.${day}`;
};
```

---

## 📁 프로젝트 구조

```
portfolio-frontend/
├── public/                 # 정적 파일
├── src/
│   ├── components/         # 공통 컴포넌트
│   │   ├── Navigation.jsx
│   │   ├── LoginModal.jsx
│   │   ├── Loading.jsx
│   │   └── ProtectedRoute.jsx
│   ├── pages/              # 페이지 컴포넌트
│   │   ├── MainPage.jsx
│   │   ├── ProjectsList.jsx
│   │   ├── ProjectDetail.jsx
│   │   └── admin/          # 관리자 페이지
│   │       ├── AdminDashboard.jsx
│   │       ├── AdminProfile.jsx
│   │       ├── AdminEducation.jsx
│   │       ├── AdminCertificates.jsx
│   │       ├── AdminProjects.jsx
│   │       └── AdminSkills.jsx
│   ├── services/           # API 서비스
│   │   └── api.js
│   ├── utils/              # 유틸리티 함수
│   │   └── dateFormat.js
│   ├── App.jsx             # 라우팅 설정
│   └── main.jsx            # 앱 진입점
├── .env                    # 환경 변수
├── package.json
├── vite.config.js
└── README.md
```

---

## 🚀 빌드 및 배포

### 개발 빌드
```bash
npm run dev
```

### 프로덕션 빌드
```bash
npm run build
```
빌드 결과물: `dist/` 폴더

### 프리뷰
```bash
npm run preview
```

### 배포 체크리스트
- [ ] `.env` 파일에 프로덕션 API URL 설정
- [ ] `npm run build` 실행
- [ ] `dist/` 폴더 확인
- [ ] 백엔드 CORS 설정 확인
- [ ] 세션 쿠키 설정 확인 (SameSite, Secure)

---

## 🐛 트러블슈팅

### 1. CORS 에러
```
Access to XMLHttpRequest at 'http://localhost:8080/api/...' from origin 'http://localhost:5173' has been blocked by CORS policy
```

**해결**: 백엔드 CORS 설정 확인
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
```

### 2. 세션 쿠키가 전송되지 않음
**해결**: `withCredentials: true` 설정 확인

### 3. 이미지 업로드 실패
**원인**: FormData 구성 오류  
**해결**: JSON 데이터를 Blob으로 변환하여 추가

### 4. 프로젝트 수정 시 새로 생성되는 문제
**원인**: `projectSeq`와 `id` 필드 혼용  
**해결**: 
```javascript
const projectId = selectedProject?.id || selectedProject?.projectSeq;
```

### 5. 이미지 수정 안 할 때 기존 이미지 삭제
**원인**: `null` 전송 시 백엔드에서 삭제 처리  
**해결**: `undefined` 전송 (FormData에 필드 미포함)

---

## 📋 주요 업데이트 내역

### 최근 변경사항
- ✅ 모든 디버깅 console.log 제거 (배포용 정리)
- ✅ 주요 성과(achievements) 필드 완전 제거
- ✅ 문제해결 섹션 단독 표시 (전체 너비)
- ✅ 자격증 자격번호 필드 추가 (certificateNumber)
- ✅ 키워드 추가 시 자동 순서 할당
- ✅ Education 카드 간격 및 가독성 개선
- ✅ 프로젝트 부제목(subtitle) 제거
- ✅ Skills 카테고리 순서 고정
- ✅ AdminProjects 한글 텍스트 영문화
- ✅ 모든 Edit/Delete 버튼 완전 표준화

### 이전 주요 업데이트
- Education과 Certificates 섹션 분리
- ProjectsList 검색/필터 기능 제거
- AdminDashboard Recent Projects 섹션 제거
- 프로젝트 상세 페이지 섹션 타이틀 최적화
- 기술스택 순서 변경 (Frontend → Backend → Database → IoT → CI/CD → External API)

---

## 📄 라이선스

개인 포트폴리오용 프로젝트입니다.
