package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기술스택 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillRequest {

    @NotBlank(message = "기술명은 필수입니다.")
    private String name;

    private String category;
    private String icon;

    @NotNull(message = "기술 레벨은 필수입니다.")
    @Min(value = 1, message = "기술 레벨은 1 이상이어야 합니다.")
    @Max(value = 5, message = "기술 레벨은 5 이하여야 합니다.")
    private Integer level;

    private Integer displayOrder;
}