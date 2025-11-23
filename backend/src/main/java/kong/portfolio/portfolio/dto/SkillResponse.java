package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillResponse {
    
    private Long id;
    private String name;
    private Integer level;
    private String category;
    
    public static SkillResponse from(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .level(skill.getLevel())
                .category(skill.getCategory())
                .build();
    }
}
