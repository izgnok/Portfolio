package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.KeywordService;
import kong.portfolio.portfolio.dto.KeywordRequest;
import kong.portfolio.portfolio.dto.KeywordResponse;
import kong.portfolio.portfolio.dto.KeywordOrderUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    /**
     * 키워드 목록 조회
     */
    @GetMapping
    public ResponseEntity<ResponseDto> getKeywords() {
        List<KeywordResponse> keywords = keywordService.getKeywords();
        return ResponseDto.response(StatusCode.SUCCESS, keywords);
    }

    /**
     * 키워드 저장
     */
    @PostMapping
    public ResponseEntity<ResponseDto> createKeyword(@Valid @RequestBody KeywordRequest request) {
        KeywordResponse keyword = keywordService.createKeyword(request);
        return ResponseDto.response(StatusCode.SUCCESS, keyword);
    }

    /**
     * 키워드 순서 일괄 변경
     */
    @PutMapping("/order")
    public ResponseEntity<ResponseDto> updateKeywordOrder(
            @Valid @RequestBody KeywordOrderUpdateRequest request) {
        keywordService.updateKeywordOrder(request);
        return ResponseDto.response(StatusCode.SUCCESS, "키워드 순서 변경 완료");
    }

    /**
     * 키워드 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteKeyword(@PathVariable Long id) {
        keywordService.deleteKeyword(id);
        return ResponseDto.response(StatusCode.SUCCESS, "키워드 삭제 완료");
    }

    /**
     * 키워드 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateKeyword(
            @PathVariable Long id,
            @Valid @RequestBody KeywordRequest request) {
        keywordService.updateKeyword(id, request.getKeyword());
        return  ResponseDto.response(StatusCode.SUCCESS, "키워드 수정 완료");
    }
}