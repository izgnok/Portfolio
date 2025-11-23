package kong.portfolio.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로젝트 상세 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailRequest {

    private String summary;
    private String coreValues;
    private String mainFeatures;
    private String myRole;
}