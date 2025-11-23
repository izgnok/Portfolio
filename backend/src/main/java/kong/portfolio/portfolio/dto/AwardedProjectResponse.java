package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwardedProjectResponse {
    
    private Long id;
    private String name;  // 프로젝트명
    private String awardName;  // 수상명
    private String awardOrganization;  // 수상 기관
    private LocalDate startDate;
    private LocalDate endDate;
    
    public static AwardedProjectResponse from(Project project) {
        return AwardedProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .awardName(project.getAwardName())
                .awardOrganization(project.getAwardOrganization())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }
}
