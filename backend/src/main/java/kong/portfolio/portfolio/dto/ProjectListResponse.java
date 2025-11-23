package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Project;
import kong.portfolio.portfolio.entity.ProjectImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectListResponse {
    
    private Long id;
    private String name;  // 프로젝트명
    private String firstImage;  // 첫번째 이미지 (Base64)
    private Integer teamSize;  // 팀인원
    private LocalDate startDate;  // 시작일
    private LocalDate endDate;  // 종료일
    private Boolean hasAward;  // 수상 여부
    private String awardName;  // 수상명
    
    public static ProjectListResponse from(Project project) {
        // 첫번째 이미지 찾기 (displayOrder = 1)
        String firstImageData = null;
        if (project.getImages() != null && !project.getImages().isEmpty()) {
            ProjectImage firstImage = project.getImages().stream()
                    .filter(img -> img.getDisplayOrder() == 1)
                    .findFirst()
                    .orElse(project.getImages().get(0));  // displayOrder=1 없으면 첫번째꺼
            
            if (firstImage != null) {
                String base64 = Base64.getEncoder().encodeToString(firstImage.getImageData());
                firstImageData = "data:" + firstImage.getImageType() + ";base64," + base64;
            }
        }
        
        return ProjectListResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .firstImage(firstImageData)
                .teamSize(project.getTeamSize())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .hasAward(project.getHasAward())
                .awardName(project.getAwardName())
                .build();
    }
}
