package kong.portfolio.portfolio.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 파일 저장 경로 설정
 */
@Configuration
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileStorageProperties {
    
    /**
     * 파일 업로드 디렉토리
     */
    private String uploadDir;
}
