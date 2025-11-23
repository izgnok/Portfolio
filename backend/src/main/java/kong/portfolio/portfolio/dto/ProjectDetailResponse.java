package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.ProjectDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로젝트 상세 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailResponse {

    private Long projectDetailSeq;
    private String summary;
    private String coreValues;
    private String mainFeatures;
    private String myRole;

    public static ProjectDetailResponse from(ProjectDetail detail) {
        if (detail == null) {
            return null;
        }
        return ProjectDetailResponse.builder()
                .projectDetailSeq(detail.getProjectDetailSeq())
                .summary(detail.getSummary())
                .coreValues(detail.getCoreValues())
                .mainFeatures(detail.getMainFeatures())
                .myRole(detail.getMyRole())
                .build();
    }
}