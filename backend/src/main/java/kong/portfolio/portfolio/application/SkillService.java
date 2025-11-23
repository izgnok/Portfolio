package kong.portfolio.portfolio.application;

import kong.portfolio.portfolio.dto.SkillRequest;
import kong.portfolio.portfolio.dto.SkillResponse;
import kong.portfolio.portfolio.entity.Skill;
import kong.portfolio.portfolio.infrastructure.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillService {
    
    private final SkillRepository skillRepository;
    
    /**
     * 스킬 목록 조회 (카테고리순, 이름순)
     */
    public List<SkillResponse> getSkills() {
        return skillRepository.findAllByOrderByCategoryAscNameAsc()
                .stream()
                .map(SkillResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 스킬 저장
     */
    @Transactional
    public SkillResponse createSkill(SkillRequest request) {
        Skill skill = Skill.builder()
                .name(request.getName())
                .level(request.getLevel())
                .category(request.getCategory())
                .build();
        
        Skill saved = skillRepository.save(skill);
        return SkillResponse.from(saved);
    }
    
    /**
     * 스킬 수정
     */
    @Transactional
    public SkillResponse updateSkill(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("스킬을 찾을 수 없습니다."));
        
        skill.update(
            request.getName(),
            request.getLevel(),
            request.getCategory()
        );
        
        return SkillResponse.from(skill);
    }
    
    /**
     * 스킬 삭제
     */
    @Transactional
    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new RuntimeException("스킬을 찾을 수 없습니다.");
        }
        skillRepository.deleteById(id);
    }
}
