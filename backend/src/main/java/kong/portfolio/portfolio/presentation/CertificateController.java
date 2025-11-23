package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.CertificateService;
import kong.portfolio.portfolio.dto.CertificateRequest;
import kong.portfolio.portfolio.dto.CertificateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllCertificates() {
        List<CertificateResponse> response = certificateService.getAllCertificates();
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @GetMapping("/{certificateSeq}")
    public ResponseEntity<ResponseDto> getCertificate(@PathVariable Long certificateSeq) {
        CertificateResponse response = certificateService.getCertificate(certificateSeq);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createCertificate(@Valid @RequestBody CertificateRequest request) {
        CertificateResponse response = certificateService.createCertificate(request);
        return ResponseDto.response(StatusCode.CREATED, response);
    }

    @PutMapping("/{certificateSeq}")
    public ResponseEntity<ResponseDto> updateCertificate(
            @PathVariable Long certificateSeq,
            @Valid @RequestBody CertificateRequest request) {
        CertificateResponse response = certificateService.updateCertificate(certificateSeq, request);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @DeleteMapping("/{certificateSeq}")
    public ResponseEntity<ResponseDto> deleteCertificate(@PathVariable Long certificateSeq) {
        certificateService.deleteCertificate(certificateSeq);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/{certificateSeq}/order")
    public ResponseEntity<ResponseDto> updateCertificateOrder(
            @PathVariable Long certificateSeq,
            @RequestParam Integer newOrder) {
        certificateService.updateCertificateOrder(certificateSeq, newOrder);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/order")
    public ResponseEntity<ResponseDto> updateCertificatesOrder(@RequestBody List<Long> certificateSeqs) {
        certificateService.updateCertificatesOrder(certificateSeqs);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }
}
