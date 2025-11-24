# 멀티 스테이지 빌드 - 1단계: 빌드
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Gradle Wrapper 복사
COPY gradlew .
COPY gradle gradle

# 빌드 설정 파일 복사
COPY build.gradle .
COPY settings.gradle .

# 의존성 다운로드 (캐싱)
RUN ./gradlew dependencies --no-daemon || true

# 소스 코드 복사
COPY src src

# 빌드 실행 (테스트 스킵)
RUN ./gradlew clean bootJar -x test --no-daemon

# 멀티 스테이지 빌드 - 2단계: 실행
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
