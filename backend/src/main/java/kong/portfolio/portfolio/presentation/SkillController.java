package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.SkillService;
import kong.portfolio.portfolio.dto.SkillRequest;
import kong.portfolio.portfolio.dto.SkillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {
    
    private final SkillService skillService;
    
    /**
     * 스킬 목록 조회 (카테고리별로 그룹화)
     */
    @GetMapping
    public ResponseEntity<ResponseDto> getSkills() {
        List<SkillResponse> skills = skillService.getSkills();
        return ResponseDto.response(StatusCode.SUCCESS, skills);
    }
    
    /**
     * 스킬 저장
     */
    @PostMapping
    public ResponseEntity<ResponseDto> createSkill(@Valid @RequestBody SkillRequest request) {
        SkillResponse skill = skillService.createSkill(request);
        return ResponseDto.response(StatusCode.SUCCESS, skill);
    }
    
    /**
     * 스킬 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody SkillRequest request) {
        SkillResponse skill = skillService.updateSkill(id, request);
        return ResponseDto.response(StatusCode.SUCCESS, skill);
    }
    
    /**
     * 스킬 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseDto.response(StatusCode.SUCCESS, "스킬 삭제 완료");
    }
}
