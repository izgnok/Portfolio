package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequest {

    @NotBlank(message = "자격증명을 입력해주세요")
    private String name;

    @NotBlank(message = "발급기관을 입력해주세요")
    private String issuer;

    @NotNull(message = "취득일자를 입력해주세요")
    private LocalDate issueDate;

    private String certificateNumber;
}
