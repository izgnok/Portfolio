package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequest {
    
    @NotBlank(message = "스킬명을 입력해주세요")
    private String name;
    
    @NotNull(message = "숙련도를 입력해주세요")
    @Min(value = 0, message = "숙련도는 0 이상이어야 합니다")
    @Max(value = 5, message = "숙련도는 5 이하여야 합니다")
    private Integer level;
    
    @NotBlank(message = "카테고리를 입력해주세요")
    private String category;  // 프론트엔드, 백엔드, CI/CD, DB, 기타
}
