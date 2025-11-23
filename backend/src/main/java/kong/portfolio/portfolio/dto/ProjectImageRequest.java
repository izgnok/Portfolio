package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로젝트 이미지 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectImageRequest {

    @NotBlank(message = "이미지 URL은 필수입니다.")
    private String imageUrl;

    private Integer displayOrder;
}