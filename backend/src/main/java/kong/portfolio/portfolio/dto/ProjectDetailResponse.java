package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailResponse {
    
    // 기본 정보
    private Long id;
    private String name;
    private Integer teamSize;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String githubUrl;
    
    // 수상 정보
    private Boolean hasAward;
    private String awardName;
    private String awardOrganization;
    
    // 시스템 아키텍처 이미지 (Base64)
    private String architectureImage;
    
    // 프로젝트 이미지들 (Base64)
    private List<ProjectImageData> projectImages;
    
    // JSON 데이터들 (프론트에서 JSON.parse()해서 사용)
    private String summaries;
    private String coreValues;
    private String mainFeatures;
    private String roles;
    
    // 기술스택
    private String techDatabase;
    private String techBackend;
    private String techFrontend;
    private String techIot;
    private String techCicd;
    private String techExternalApi;
    
    // 성과 및 문제해결
    private String problemSolutions;
    private String achievements;
    
    // 아쉬운점/개선방안
    private String regrets;
    private String improvements;
    
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectImageData {
        private Long id;
        private String imageData;  // Base64
        private Integer displayOrder;
    }
    
    public static ProjectDetailResponse from(Project project) {
        // 시스템 아키텍처 이미지 처리
        String architectureImageData = null;
        if (project.getArchitectureImage() != null) {
            String base64 = Base64.getEncoder().encodeToString(project.getArchitectureImage());
            architectureImageData = "data:" + project.getArchitectureImageType() + ";base64," + base64;
        }
        
        // 프로젝트 이미지들 처리
        List<ProjectImageData> projectImagesData = project.getImages().stream()
                .map(img -> {
                    String base64 = Base64.getEncoder().encodeToString(img.getImageData());
                    String imageData = "data:" + img.getImageType() + ";base64," + base64;
                    return ProjectImageData.builder()
                            .id(img.getId())
                            .imageData(imageData)
                            .displayOrder(img.getDisplayOrder())
                            .build();
                })
                .collect(Collectors.toList());
        
        return ProjectDetailResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .teamSize(project.getTeamSize())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .status(project.getStatus())
                .githubUrl(project.getGithubUrl())
                .hasAward(project.getHasAward())
                .awardName(project.getAwardName())
                .awardOrganization(project.getAwardOrganization())
                .architectureImage(architectureImageData)
                .projectImages(projectImagesData)
                .summaries(project.getSummaries())
                .coreValues(project.getCoreValues())
                .mainFeatures(project.getMainFeatures())
                .roles(project.getRoles())
                .techDatabase(project.getTechDatabase())
                .techBackend(project.getTechBackend())
                .techFrontend(project.getTechFrontend())
                .techIot(project.getTechIot())
                .techCicd(project.getTechCicd())
                .techExternalApi(project.getTechExternalApi())
                .problemSolutions(project.getProblemSolutions())
                .achievements(project.getAchievements())
                .regrets(project.getRegrets())
                .improvements(project.getImprovements())
                .build();
    }
}
