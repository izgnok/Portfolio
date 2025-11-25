package kong.portfolio.portfolio.application;

import kong.portfolio.portfolio.dto.CertificateRequest;
import kong.portfolio.portfolio.dto.CertificateResponse;
import kong.portfolio.portfolio.entity.Certificate;
import kong.portfolio.portfolio.infrastructure.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CertificateService {

    private final CertificateRepository certificateRepository;

    /**
     * 자격증 목록 조회 (최신순)
     */
    public List<CertificateResponse> getCertificates() {
        return certificateRepository.findAllByOrderByIssueDateDesc()
                .stream()
                .map(CertificateResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 자격증 추가
     */
    @Transactional
    public CertificateResponse createCertificate(CertificateRequest request) {
        Certificate certificate = Certificate.builder()
                .name(request.getName())
                .issuer(request.getIssuer())
                .issueDate(request.getIssueDate())
                .build();

        Certificate saved = certificateRepository.save(certificate);
        return CertificateResponse.from(saved);
    }

    /**
     * 자격증 수정
     */
    @Transactional
    public CertificateResponse updateCertificate(Long id, CertificateRequest request) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("자격증을 찾을 수 없습니다."));

        certificate.update(
                request.getName(),
                request.getIssuer(),
                request.getCertificateNumber(),
                request.getIssueDate()
        );

        return CertificateResponse.from(certificate);
    }

    /**
     * 자격증 삭제
     */
    @Transactional
    public void deleteCertificate(Long id) {
        if (!certificateRepository.existsById(id)) {
            throw new RuntimeException("자격증을 찾을 수 없습니다.");
        }
        certificateRepository.deleteById(id);
    }
}
