package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Keyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 키워드 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeywordResponse {

    private Long keywordSeq;
    private String content;
    private Integer displayOrder;

    public static KeywordResponse from(Keyword keyword) {
        return KeywordResponse.builder()
                .keywordSeq(keyword.getKeywordSeq())
                .content(keyword.getContent())
                .displayOrder(keyword.getDisplayOrder())
                .build();
    }
}