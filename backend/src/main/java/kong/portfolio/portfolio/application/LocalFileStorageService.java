package kong.portfolio.portfolio.application;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.config.FileStorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 로컬 파일 시스템 저장 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocalFileStorageService implements FileStorageService {

    private final FileStorageProperties fileStorageProperties;
    private Path fileStorageLocation;

    /**
     * 초기화: 업로드 디렉토리 생성
     */
    @jakarta.annotation.PostConstruct
    public void init() {
        try {
            this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                    .toAbsolutePath()
                    .normalize();
            Files.createDirectories(this.fileStorageLocation);
            log.info("파일 저장 디렉토리 생성 완료: {}", this.fileStorageLocation);
        } catch (Exception e) {
            throw new RestApiException(StatusCode.FILE_STORAGE_INIT_FAILED);
        }
    }

    /**
     * 파일 저장
     */
    @Override
    public String storeFile(MultipartFile file) {
        // 파일명 검증
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (originalFileName.contains("..")) {
            throw new RestApiException(StatusCode.INVALID_FILE_NAME);
        }

        // 파일 확장자 추출
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }

        // UUID로 고유 파일명 생성
        String storedFileName = UUID.randomUUID().toString() + extension;

        try {
            Path targetLocation = this.fileStorageLocation.resolve(storedFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("파일 저장 완료: {}", storedFileName);
            return storedFileName;
        } catch (IOException e) {
            log.error("파일 저장 실패: {}", originalFileName, e);
            throw new RestApiException(StatusCode.FILE_UPLOAD_FAILED);
        }
    }

    /**
     * 파일 삭제
     */
    @Override
    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
            log.info("파일 삭제 완료: {}", fileName);
        } catch (IOException e) {
            log.error("파일 삭제 실패: {}", fileName, e);
            throw new RestApiException(StatusCode.FILE_DELETE_FAILED);
        }
    }

    /**
     * 파일 URL 반환
     */
    @Override
    public String getFileUrl(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        return "/api/files/" + fileName;
    }
}
