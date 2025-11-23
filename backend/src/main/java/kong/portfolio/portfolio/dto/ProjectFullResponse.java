package kong.portfolio.portfolio.dto;


import kong.portfolio.portfolio.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 프로젝트 전체 응답 DTO (연관 엔티티 포함)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectFullResponse {

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

    // 연관 엔티티
    private ProjectDetailResponse detail;
    private List<ProjectImageResponse> images;
    private List<SkillResponse> techStacks;
    private List<AchievementResponse> achievements;

    public static ProjectFullResponse from(Project project) {
        return ProjectFullResponse.builder()
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
                .detail(ProjectDetailResponse.from(project.getProjectDetail()))
                .images(project.getProjectImages().stream()
                        .map(ProjectImageResponse::from)
                        .collect(Collectors.toList()))
                .techStacks(project.getProjectTechStacks().stream()
                        .map(pts -> SkillResponse.from(pts.getSkill()))
                        .collect(Collectors.toList()))
                .achievements(project.getAchievements().stream()
                        .map(AchievementResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}