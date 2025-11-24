# 멀티 스테이지 빌드 - 1단계: Gradle 빌드
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Gradle 래퍼 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 의존성 다운로드 (캐싱 최적화)
COPY gradle gradle
RUN gradle dependencies --no-daemon || true

# 소스 코드 복사
COPY src src

# 빌드 실행
RUN gradle clean build -x test --no-daemon

# 멀티 스테이지 빌드 - 2단계: 실행 환경
FROM eclipse-temurin:17-jre

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8081

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
