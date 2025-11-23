package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 키워드 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeywordRequest {

    @NotBlank(message = "키워드 내용은 필수입니다.")
    private String content;

    private Integer displayOrder;
}