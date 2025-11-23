package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.CertificateService;
import kong.portfolio.portfolio.dto.CertificateRequest;
import kong.portfolio.portfolio.dto.CertificateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {
    
    private final CertificateService certificateService;
    
    /**
     * 자격증 목록 조회 (최신순)
     */
    @GetMapping
    public ResponseEntity<ResponseDto> getCertificates() {
        List<CertificateResponse> certificates = certificateService.getCertificates();
        return ResponseDto.response(StatusCode.SUCCESS, certificates);
    }
    
    /**
     * 자격증 추가
     */
    @PostMapping
    public ResponseEntity<ResponseDto> createCertificate(@Valid @RequestBody CertificateRequest request) {
        CertificateResponse certificate = certificateService.createCertificate(request);
        return ResponseDto.response(StatusCode.SUCCESS, certificate);
    }
    
    /**
     * 자격증 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateCertificate(
            @PathVariable Long id,
            @Valid @RequestBody CertificateRequest request) {
        CertificateResponse certificate = certificateService.updateCertificate(id, request);
        return ResponseDto.response(StatusCode.SUCCESS, certificate);
    }
    
    /**
     * 자격증 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificate(id);
        return ResponseDto.response(StatusCode.SUCCESS, "자격증 삭제 완료");
    }
}
