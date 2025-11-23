package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.FileStorageService;
import kong.portfolio.portfolio.config.FileStorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 파일 업로드/다운로드 Controller
 */
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileStorageService fileStorageService;
    private final FileStorageProperties fileStorageProperties;

    /**
     * 파일 업로드
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> uploadFile(
            @RequestParam("file") MultipartFile file) {

        log.info("파일 업로드 요청: {}", file.getOriginalFilename());

        String fileName = fileStorageService.storeFile(file);
        String fileUrl = fileStorageService.getFileUrl(fileName);

        Map<String, String> result = new HashMap<>();
        result.put("fileName", fileName);
        result.put("fileUrl", fileUrl);
        result.put("originalFileName", file.getOriginalFilename());

        return ResponseDto.response(StatusCode.FILE_UPLOAD_SUCCESS, result);
    }

    /**
     * 파일 다운로드/조회
     */
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                    .toAbsolutePath()
                    .normalize();

            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RestApiException(StatusCode.FILE_NOT_FOUND);
            }

            // Content-Type 결정
            String contentType = "application/octet-stream";
            String fileNameLower = fileName.toLowerCase();

            if (fileNameLower.endsWith(".jpg") || fileNameLower.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (fileNameLower.endsWith(".png")) {
                contentType = "image/png";
            } else if (fileNameLower.endsWith(".gif")) {
                contentType = "image/gif";
            } else if (fileNameLower.endsWith(".pdf")) {
                contentType = "application/pdf";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (Exception e) {
            log.error("파일 다운로드 실패: {}", fileName, e);
            throw new RestApiException(StatusCode.FILE_DOWNLOAD_FAILED);
        }
    }

    /**
     * 파일 삭제
     */
    @DeleteMapping("/{fileName:.+}")
    public ResponseEntity<ResponseDto> deleteFile(@PathVariable String fileName) {
        log.info("파일 삭제 요청: {}", fileName);
        fileStorageService.deleteFile(fileName);
        return ResponseDto.response(StatusCode.FILE_DELETE_SUCCESS, null);
    }
}
