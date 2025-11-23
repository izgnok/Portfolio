package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 학력 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationRequest {

    @NotBlank(message = "기관명은 필수입니다.")
    private String institution;

    private String major;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String gpa;
    private String description;
    private Integer displayOrder;
}