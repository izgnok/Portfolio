package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기술스택 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillResponse {

    private Long skillSeq;
    private String name;
    private String category;
    private String icon;
    private Integer level;
    private Integer displayOrder;

    public static SkillResponse from(Skill skill) {
        return SkillResponse.builder()
                .skillSeq(skill.getSkillSeq())
                .name(skill.getName())
                .category(skill.getCategory())
                .icon(skill.getIcon())
                .level(skill.getLevel())
                .displayOrder(skill.getDisplayOrder())
                .build();
    }
}