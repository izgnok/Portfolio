import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL; // ← 이렇게 수정

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

// 요청 인터셉터 - 세션 기반 인증
api.interceptors.request.use(
  (config) => {
    // withCredentials: true로 세션 쿠키가 자동으로 전송됨
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터
api.interceptors.response.use(
  (response) => {
    // 백엔드 ResponseDto 구조: { data, message, timestamp, httpStatus, serviceStatus }
    return response.data;
  },
  (error) => {
    const message = error.response?.data?.message || "오류가 발생했습니다.";
    return Promise.reject(new Error(message));
  }
);

// ============================================
// Auth API (세션 기반 인증)
// ============================================
export const authAPI = {
  // POST /api/users/login - LoginRequest {username, password}
  login: (data) => api.post("/users/login", data),

  // POST /api/users/logout - 세션 무효화
  logout: () => api.post("/users/logout"),
};

// ============================================
// Profile API (Base64 이미지)
// ============================================
export const profileAPI = {
  // GET /api/profile - ProfileResponse
  get: () => api.get("/profile"),

  // POST /api/profile - @RequestPart 사용
  // profileData: ProfileRequest (JSON)
  // profileImage: File (선택)
  save: async (profileData, profileImage) => {
    const formData = new FormData();

    // ProfileRequest를 JSON Blob으로 추가
    const profileBlob = new Blob([JSON.stringify(profileData)], {
      type: "application/json",
    });
    formData.append("profile", profileBlob);

    // 이미지 파일 추가 (선택사항)
    if (profileImage) {
      formData.append("profileImage", profileImage);
    }

    // api 인스턴스 사용 (세션 쿠키 자동 포함)
    return await api.post("/profile", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
};

// ============================================
// Keywords API
// ============================================
export const keywordsAPI = {
  // GET /api/keywords - List<KeywordResponse>
  getAll: () => api.get("/keywords"),

  // POST /api/keywords - KeywordRequest {keyword, displayOrder}
  create: (data) => api.post("/keywords", data),

  // DELETE /api/keywords/{id}
  delete: (id) => api.delete(`/keywords/${id}`),

  // PUT /api/keywords/order - KeywordOrderUpdateRequest {keywords: [{id, displayOrder}]}
  updateOrder: (data) => api.put("/keywords/order", data),
};

// ============================================
// Educations API
// ============================================
export const educationsAPI = {
  // GET /api/educations - List<EducationResponse>
  getAll: () => api.get("/educations"),

  // POST /api/educations - EducationRequest {name, startDate, endDate, status, gpa}
  create: (data) => api.post("/educations", data),

  // PUT /api/educations/{id} - EducationRequest
  update: (id, data) => api.put(`/educations/${id}`, data),

  // DELETE /api/educations/{id}
  delete: (id) => api.delete(`/educations/${id}`),
};

// ============================================
// Certificates API
// ============================================
export const certificatesAPI = {
  // GET /api/certificates - List<CertificateResponse>
  getAll: () => api.get("/certificates"),

  // POST /api/certificates - CertificateRequest {name, issuer, issueDate}
  create: (data) => api.post("/certificates", data),

  // PUT /api/certificates/{id} - CertificateRequest
  update: (id, data) => api.put(`/certificates/${id}`, data),

  // DELETE /api/certificates/{id}
  delete: (id) => api.delete(`/certificates/${id}`),
};

// ============================================
// Skills API
// ============================================
export const skillsAPI = {
  // GET /api/skills - List<SkillResponse>
  getAll: () => api.get("/skills"),

  // POST /api/skills - SkillRequest {name, level, category}
  create: (data) => api.post("/skills", data),

  // PUT /api/skills/{id} - SkillRequest
  update: (id, data) => api.put(`/skills/${id}`, data),

  // DELETE /api/skills/{id}
  delete: (id) => api.delete(`/skills/${id}`),
};

// ============================================
// Projects API (Base64 이미지)
// ============================================
export const projectsAPI = {
  // GET /api/projects - List<ProjectListResponse>
  getAll: () => api.get("/projects"),

  // GET /api/projects/{id} - ProjectDetailResponse
  getById: (id) => api.get(`/projects/${id}`),

  // GET /api/projects/awarded - List<AwardedProjectResponse> (수상작만 조회)
  getAwarded: () => api.get("/projects/awarded"),

  // POST /api/projects - @RequestPart 사용
  // projectData: ProjectRequest (JSON)
  // projectImages: File[] (선택)
  // architectureImage: File (선택)
  create: async (projectData, projectImages, architectureImage) => {
    const formData = new FormData();

    // ProjectRequest를 JSON Blob으로 추가
    const projectBlob = new Blob([JSON.stringify(projectData)], {
      type: "application/json",
    });
    formData.append("project", projectBlob);

    // 프로젝트 이미지들 추가 (선택사항)
    if (projectImages && projectImages.length > 0) {
      projectImages.forEach((image) => {
        formData.append("projectImages", image);
      });
    }

    // 아키텍처 이미지 추가 (선택사항)
    if (architectureImage) {
      formData.append("architectureImage", architectureImage);
    }

    // api 인스턴스 사용 (세션 쿠키 자동 포함)
    return await api.post("/projects", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },

  // PUT /api/projects/{id} - @RequestPart 사용
  update: async (id, projectData, projectImages, architectureImage) => {
    const formData = new FormData();

    // ProjectRequest를 JSON Blob으로 추가
    const projectBlob = new Blob([JSON.stringify(projectData)], {
      type: "application/json",
    });
    formData.append("project", projectBlob);

    // 프로젝트 이미지들 추가 (선택사항)
    if (projectImages && projectImages.length > 0) {
      projectImages.forEach((image) => {
        formData.append("projectImages", image);
      });
    }

    // 아키텍처 이미지 추가 (선택사항)
    if (architectureImage) {
      formData.append("architectureImage", architectureImage);
    }

    // api 인스턴스 사용 (세션 쿠키 자동 포함)
    return await api.put(`/projects/${id}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },

  // DELETE /api/projects/{id}
  delete: (id) => api.delete(`/projects/${id}`),
};

export default api;
