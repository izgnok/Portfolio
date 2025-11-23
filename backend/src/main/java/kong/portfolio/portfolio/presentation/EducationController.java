package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.EducationService;
import kong.portfolio.portfolio.dto.EducationRequest;
import kong.portfolio.portfolio.dto.EducationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllEducations() {
        List<EducationResponse> response = educationService.getAllEducations();
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @GetMapping("/{educationSeq}")
    public ResponseEntity<ResponseDto> getEducation(@PathVariable Long educationSeq) {
        EducationResponse response = educationService.getEducation(educationSeq);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createEducation(@Valid @RequestBody EducationRequest request) {
        EducationResponse response = educationService.createEducation(request);
        return ResponseDto.response(StatusCode.CREATED, response);
    }

    @PutMapping("/{educationSeq}")
    public ResponseEntity<ResponseDto> updateEducation(
            @PathVariable Long educationSeq,
            @Valid @RequestBody EducationRequest request) {
        EducationResponse response = educationService.updateEducation(educationSeq, request);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @DeleteMapping("/{educationSeq}")
    public ResponseEntity<ResponseDto> deleteEducation(@PathVariable Long educationSeq) {
        educationService.deleteEducation(educationSeq);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/{educationSeq}/order")
    public ResponseEntity<ResponseDto> updateEducationOrder(
            @PathVariable Long educationSeq,
            @RequestParam Integer newOrder) {
        educationService.updateEducationOrder(educationSeq, newOrder);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/order")
    public ResponseEntity<ResponseDto> updateEducationsOrder(@RequestBody List<Long> educationSeqs) {
        educationService.updateEducationsOrder(educationSeqs);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }
}
