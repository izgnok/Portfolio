# 멀티 스테이지 빌드 - 1단계: 빌드
FROM node:18-alpine AS build

WORKDIR /app

# package.json과 package-lock.json 복사
COPY package*.json ./

# 의존성 설치
RUN npm install

# 소스 코드 복사
COPY . .

# 빌드 실행
RUN npm run build

# 멀티 스테이지 빌드 - 2단계: Nginx로 서빙
FROM nginx:alpine

# 빌드된 파일 복사
COPY --from=build /app/dist /usr/share/nginx/html

# Nginx 설정 (기본값 사용)
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
