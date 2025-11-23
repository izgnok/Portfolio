package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordRequest {
    
    @NotBlank(message = "키워드를 입력해주세요")
    private String keyword;
    
    @NotNull(message = "표시 순서를 입력해주세요")
    private Integer displayOrder;
}
