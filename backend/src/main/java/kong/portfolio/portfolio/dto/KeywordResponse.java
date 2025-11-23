package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Keyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeywordResponse {
    
    private Long id;
    private String keyword;
    private Integer displayOrder;
    
    public static KeywordResponse from(Keyword keyword) {
        return KeywordResponse.builder()
                .id(keyword.getId())
                .keyword(keyword.getKeyword())
                .displayOrder(keyword.getDisplayOrder())
                .build();
    }
}
