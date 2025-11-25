package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateResponse {

    private Long id;
    private String name;
    private String issuer;
    private String certificateNumber;
    private LocalDate issueDate;

    public static CertificateResponse from(Certificate certificate) {
        return CertificateResponse.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .issuer(certificate.getIssuer())
                .certificateNumber(certificate.getCertificateNumber())
                .issueDate(certificate.getIssueDate())
                .build();
    }
}
