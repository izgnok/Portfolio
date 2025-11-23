package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Achievement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 성과 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementResponse {

    private Long achievementSeq;
    private String title;
    private String problem;
    private String solution;
    private Integer displayOrder;

    public static AchievementResponse from(Achievement achievement) {
        return AchievementResponse.builder()
                .achievementSeq(achievement.getAchievementSeq())
                .title(achievement.getTitle())
                .problem(achievement.getProblem())
                .solution(achievement.getSolution())
                .displayOrder(achievement.getDisplayOrder())
                .build();
    }
}