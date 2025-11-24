# 멀티스테이지 빌드: 1단계 - Gradle 빌드
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Gradle Wrapper 복사
COPY gradlew .
COPY gradle gradle

# 의존성 캐싱을 위한 build.gradle 먼저 복사
COPY build.gradle .
COPY settings.gradle .

# 의존성 다운로드 (캐싱 활용)
RUN ./gradlew dependencies --no-daemon || return 0

# 소스 코드 복사 및 빌드
COPY src src
RUN ./gradlew clean build -x test --no-daemon

# 2단계 - 실행 환경
FROM eclipse-temurin:17-jre

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Xmx512m", "-XX:MaxMetaspaceSize=256m", "app.jar"]
