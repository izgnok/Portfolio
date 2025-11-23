package kong.portfolio.portfolio.application;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.dto.SkillRequest;
import kong.portfolio.portfolio.dto.SkillResponse;
import kong.portfolio.portfolio.entity.Skill;
import kong.portfolio.portfolio.infrastructure.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 기술스택 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillService {

    private final SkillRepository skillRepository;

    /**
     * 모든 기술스택 조회
     */
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(SkillResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리별 기술스택 조회
     */
    public List<SkillResponse> getSkillsByCategory(String category) {
        return skillRepository.findAllByCategoryOrderByDisplayOrderAsc(category).stream()
                .map(SkillResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 기술스택 단건 조회
     */
    public SkillResponse getSkill(Long skillSeq) {
        Skill skill = skillRepository.findById(skillSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.SKILL_NOT_FOUND));
        
        return SkillResponse.from(skill);
    }

    /**
     * 기술스택 생성
     */
    @Transactional
    public SkillResponse createSkill(SkillRequest request) {
        // 카테고리별 다음 순서 계산
        int nextOrder = (int) skillRepository.countByCategory(request.getCategory());

        Skill skill = Skill.builder()
                .name(request.getName())
                .category(request.getCategory())
                .icon(request.getIcon())
                .level(request.getLevel())
                .displayOrder(nextOrder)
                .build();

        Skill savedSkill = skillRepository.save(skill);
        log.info("기술스택 생성 완료: {}", savedSkill.getSkillSeq());
        
        return SkillResponse.from(savedSkill);
    }

    /**
     * 기술스택 수정
     */
    @Transactional
    public SkillResponse updateSkill(Long skillSeq, SkillRequest request) {
        Skill skill = skillRepository.findById(skillSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.SKILL_NOT_FOUND));

        skill.updateSkill(
                request.getName(),
                request.getCategory(),
                request.getIcon(),
                request.getLevel()
        );

        log.info("기술스택 수정 완료: {}", skillSeq);
        return SkillResponse.from(skill);
    }

    /**
     * 기술스택 레벨 변경
     */
    @Transactional
    public void updateSkillLevel(Long skillSeq, Integer level) {
        Skill skill = skillRepository.findById(skillSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.SKILL_NOT_FOUND));

        skill.updateLevel(level);
        log.info("기술스택 레벨 변경 완료: {} -> {}", skillSeq, level);
    }

    /**
     * 기술스택 삭제
     */
    @Transactional
    public void deleteSkill(Long skillSeq) {
        Skill skill = skillRepository.findById(skillSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.SKILL_NOT_FOUND));

        skillRepository.delete(skill);
        log.info("기술스택 삭제 완료: {}", skillSeq);
    }

    /**
     * 기술스택 순서 변경
     */
    @Transactional
    public void updateSkillOrder(Long skillSeq, Integer newOrder) {
        Skill skill = skillRepository.findById(skillSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.SKILL_NOT_FOUND));

        skill.updateDisplayOrder(newOrder);
        log.info("기술스택 순서 변경 완료: {} -> {}", skillSeq, newOrder);
    }

    /**
     * 기술스택 일괄 순서 변경 (카테고리 내)
     */
    @Transactional
    public void updateSkillsOrder(String category, List<Long> skillSeqs) {
        for (int i = 0; i < skillSeqs.size(); i++) {
            Long skillSeq = skillSeqs.get(i);
            Skill skill = skillRepository.findById(skillSeq)
                    .orElseThrow(() -> new RestApiException(StatusCode.SKILL_NOT_FOUND));
            
            // 카테고리 검증
            if (!category.equals(skill.getCategory())) {
                throw new RestApiException(StatusCode.INVALID_INPUT_VALUE,
                        "다른 카테고리의 기술스택입니다: " + skill.getName());
            }
            
            skill.updateDisplayOrder(i);
        }
        
        log.info("기술스택 일괄 순서 변경 완료 [{}]: {} 건", category, skillSeqs.size());
    }
}
