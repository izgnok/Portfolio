package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 자격증 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateResponse {

    private Long certificateSeq;
    private String name;
    private String issuer;
    private LocalDate issueDate;
    private String credentialId;
    private String description;
    private Integer displayOrder;

    public static CertificateResponse from(Certificate certificate) {
        return CertificateResponse.builder()
                .certificateSeq(certificate.getCertificateSeq())
                .name(certificate.getName())
                .issuer(certificate.getIssuer())
                .issueDate(certificate.getIssueDate())
                .credentialId(certificate.getCredentialId())
                .description(certificate.getDescription())
                .displayOrder(certificate.getDisplayOrder())
                .build();
    }
}