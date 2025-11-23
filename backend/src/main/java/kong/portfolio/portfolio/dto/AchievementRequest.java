package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 성과 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementRequest {

    @NotBlank(message = "성과 제목은 필수입니다.")
    private String title;

    private String problem;
    private String solution;
    private Integer displayOrder;
}