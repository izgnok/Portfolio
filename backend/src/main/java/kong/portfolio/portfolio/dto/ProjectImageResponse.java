package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.ProjectImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로젝트 이미지 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectImageResponse {

    private Long projectImageSeq;
    private String imageUrl;
    private Integer displayOrder;

    public static ProjectImageResponse from(ProjectImage image) {
        return ProjectImageResponse.builder()
                .projectImageSeq(image.getProjectImageSeq())
                .imageUrl(image.getImageUrl())
                .displayOrder(image.getDisplayOrder())
                .build();
    }
}