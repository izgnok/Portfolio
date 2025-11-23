package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Education;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 학력 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationResponse {

    private Long educationSeq;
    private String institution;
    private String major;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String gpa;
    private String description;
    private Integer displayOrder;

    public static EducationResponse from(Education education) {
        return EducationResponse.builder()
                .educationSeq(education.getEducationSeq())
                .institution(education.getInstitution())
                .major(education.getMajor())
                .degree(education.getDegree())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .gpa(education.getGpa())
                .description(education.getDescription())
                .displayOrder(education.getDisplayOrder())
                .build();
    }
}