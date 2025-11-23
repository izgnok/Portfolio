package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.SkillService;
import kong.portfolio.portfolio.dto.SkillRequest;
import kong.portfolio.portfolio.dto.SkillResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllSkills() {
        List<SkillResponse> response = skillService.getAllSkills();
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ResponseDto> getSkillsByCategory(@PathVariable String category) {
        List<SkillResponse> response = skillService.getSkillsByCategory(category);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @GetMapping("/{skillSeq}")
    public ResponseEntity<ResponseDto> getSkill(@PathVariable Long skillSeq) {
        SkillResponse response = skillService.getSkill(skillSeq);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createSkill(@Valid @RequestBody SkillRequest request) {
        SkillResponse response = skillService.createSkill(request);
        return ResponseDto.response(StatusCode.CREATED, response);
    }

    @PutMapping("/{skillSeq}")
    public ResponseEntity<ResponseDto> updateSkill(
            @PathVariable Long skillSeq,
            @Valid @RequestBody SkillRequest request) {
        SkillResponse response = skillService.updateSkill(skillSeq, request);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PatchMapping("/{skillSeq}/level")
    public ResponseEntity<ResponseDto> updateSkillLevel(
            @PathVariable Long skillSeq,
            @RequestParam Integer level) {
        skillService.updateSkillLevel(skillSeq, level);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @DeleteMapping("/{skillSeq}")
    public ResponseEntity<ResponseDto> deleteSkill(@PathVariable Long skillSeq) {
        skillService.deleteSkill(skillSeq);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/{skillSeq}/order")
    public ResponseEntity<ResponseDto> updateSkillOrder(
            @PathVariable Long skillSeq,
            @RequestParam Integer newOrder) {
        skillService.updateSkillOrder(skillSeq, newOrder);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/category/{category}/order")
    public ResponseEntity<ResponseDto> updateSkillsOrder(
            @PathVariable String category,
            @RequestBody List<Long> skillSeqs) {
        skillService.updateSkillsOrder(category, skillSeqs);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }
}
