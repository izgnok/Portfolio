package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.EducationService;
import kong.portfolio.portfolio.dto.EducationRequest;
import kong.portfolio.portfolio.dto.EducationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    /**
     * 학력 목록 조회 (최신순)
     */
    @GetMapping
    public ResponseEntity<ResponseDto> getEducations() {
        List<EducationResponse> educations = educationService.getEducations();
        return ResponseDto.response(StatusCode.SUCCESS, educations);
    }

    /**
     * 학력 저장
     */
    @PostMapping
    public ResponseEntity<ResponseDto> createEducation(@Valid @RequestBody EducationRequest request) {
        EducationResponse education = educationService.createEducation(request);
        return ResponseDto.response(StatusCode.SUCCESS, education);
    }

    /**
     * 학력 수정 (추가)
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateEducation(
            @PathVariable Long id,
            @Valid @RequestBody EducationRequest request) {
        EducationResponse education = educationService.updateEducation(id, request);
        return ResponseDto.response(StatusCode.SUCCESS, education);
    }

    /**
     * 학력 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseDto.response(StatusCode.SUCCESS, "학력 삭제 완료");
    }
}
