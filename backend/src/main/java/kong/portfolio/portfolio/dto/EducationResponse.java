package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Education;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationResponse {
    
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String gpa;
    
    public static EducationResponse from(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .name(education.getName())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .status(education.getStatus())
                .gpa(education.getGpa())
                .build();
    }
}
