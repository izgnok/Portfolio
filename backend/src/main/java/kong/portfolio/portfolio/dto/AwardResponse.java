package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Award;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 수상 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwardResponse {

    private Long awardSeq;
    private String title;
    private String organization;
    private LocalDate awardDate;
    private String rank;
    private String description;
    private Integer displayOrder;

    public static AwardResponse from(Award award) {
        return AwardResponse.builder()
                .awardSeq(award.getAwardSeq())
                .title(award.getTitle())
                .organization(award.getOrganization())
                .awardDate(award.getAwardDate())
                .rank(award.getRank())
                .description(award.getDescription())
                .displayOrder(award.getDisplayOrder())
                .build();
    }
}