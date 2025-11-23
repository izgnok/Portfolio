package kong.portfolio.portfolio.application;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 저장 서비스 인터페이스
 * (향후 AWS S3 등으로 쉽게 교체 가능)
 */
public interface FileStorageService {
    
    /**
     * 파일 저장
     * @param file 업로드할 파일
     * @return 저장된 파일명 (UUID 포함)
     */
    String storeFile(MultipartFile file);
    
    /**
     * 파일 삭제
     * @param fileName 삭제할 파일명
     */
    void deleteFile(String fileName);
    
    /**
     * 파일 URL 반환
     * @param fileName 파일명
     * @return 파일 접근 URL
     */
    String getFileUrl(String fileName);
}
