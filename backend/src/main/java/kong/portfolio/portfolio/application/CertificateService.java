package kong.portfolio.portfolio.application;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.dto.CertificateRequest;
import kong.portfolio.portfolio.dto.CertificateResponse;
import kong.portfolio.portfolio.entity.Certificate;
import kong.portfolio.portfolio.infrastructure.CertificateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 자격증 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CertificateService {

    private final CertificateRepository certificateRepository;

    /**
     * 모든 자격증 조회
     */
    public List<CertificateResponse> getAllCertificates() {
        return certificateRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(CertificateResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 자격증 단건 조회
     */
    public CertificateResponse getCertificate(Long certificateSeq) {
        Certificate certificate = certificateRepository.findById(certificateSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.CERTIFICATE_NOT_FOUND));
        
        return CertificateResponse.from(certificate);
    }

    /**
     * 자격증 생성
     */
    @Transactional
    public CertificateResponse createCertificate(CertificateRequest request) {
        // 다음 순서 계산
        int nextOrder = (int) certificateRepository.count();

        Certificate certificate = Certificate.builder()
                .name(request.getName())
                .issuer(request.getIssuer())
                .issueDate(request.getIssueDate())
                .credentialId(request.getCredentialId())
                .description(request.getDescription())
                .displayOrder(nextOrder)
                .build();

        Certificate savedCertificate = certificateRepository.save(certificate);
        log.info("자격증 생성 완료: {}", savedCertificate.getCertificateSeq());
        
        return CertificateResponse.from(savedCertificate);
    }

    /**
     * 자격증 수정
     */
    @Transactional
    public CertificateResponse updateCertificate(Long certificateSeq, CertificateRequest request) {
        Certificate certificate = certificateRepository.findById(certificateSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.CERTIFICATE_NOT_FOUND));

        certificate.updateCertificate(
                request.getName(),
                request.getIssuer(),
                request.getIssueDate(),
                request.getCredentialId(),
                request.getDescription()
        );

        log.info("자격증 수정 완료: {}", certificateSeq);
        return CertificateResponse.from(certificate);
    }

    /**
     * 자격증 삭제
     */
    @Transactional
    public void deleteCertificate(Long certificateSeq) {
        Certificate certificate = certificateRepository.findById(certificateSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.CERTIFICATE_NOT_FOUND));

        certificateRepository.delete(certificate);
        log.info("자격증 삭제 완료: {}", certificateSeq);
    }

    /**
     * 자격증 순서 변경
     */
    @Transactional
    public void updateCertificateOrder(Long certificateSeq, Integer newOrder) {
        Certificate certificate = certificateRepository.findById(certificateSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.CERTIFICATE_NOT_FOUND));

        certificate.updateDisplayOrder(newOrder);
        log.info("자격증 순서 변경 완료: {} -> {}", certificateSeq, newOrder);
    }

    /**
     * 자격증 일괄 순서 변경
     */
    @Transactional
    public void updateCertificatesOrder(List<Long> certificateSeqs) {
        for (int i = 0; i < certificateSeqs.size(); i++) {
            Long certificateSeq = certificateSeqs.get(i);
            Certificate certificate = certificateRepository.findById(certificateSeq)
                    .orElseThrow(() -> new RestApiException(StatusCode.CERTIFICATE_NOT_FOUND));
            
            certificate.updateDisplayOrder(i);
        }
        
        log.info("자격증 일괄 순서 변경 완료: {} 건", certificateSeqs.size());
    }
}
