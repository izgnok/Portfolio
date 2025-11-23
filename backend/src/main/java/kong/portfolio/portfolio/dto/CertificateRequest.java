package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 자격증 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateRequest {

    @NotBlank(message = "자격증명은 필수입니다.")
    private String name;

    private String issuer;
    private LocalDate issueDate;
    private String credentialId;
    private String description;
    private Integer displayOrder;
}