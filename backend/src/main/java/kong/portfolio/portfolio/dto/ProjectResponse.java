package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 프로젝트 응답 DTO (기본 정보)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {

    private Long projectSeq;
    private String title;
    private String subtitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer teamSize;
    private String projectType;
    private String award;
    private String githubUrl;
    private String demoUrl;
    private String icon;
    private Integer displayOrder;

    public static ProjectResponse from(Project project) {
        return ProjectResponse.builder()
                .projectSeq(project.getProjectSeq())
                .title(project.getTitle())
                .subtitle(project.getSubtitle())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .teamSize(project.getTeamSize())
                .projectType(project.getProjectType())
                .award(project.getAward())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .icon(project.getIcon())
                .displayOrder(project.getDisplayOrder())
                .build();
    }
}