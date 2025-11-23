package kong.portfolio.portfolio.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordOrderUpdateRequest {
    
    @NotEmpty(message = "키워드 순서 목록은 비어있을 수 없습니다")
    @Valid
    private List<KeywordOrderItem> keywords;
    
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KeywordOrderItem {
        
        @NotNull(message = "키워드 ID는 필수입니다")
        private Long id;
        
        @NotNull(message = "표시 순서는 필수입니다")
        private Integer displayOrder;
    }
}