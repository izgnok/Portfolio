package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.KeywordService;
import kong.portfolio.portfolio.dto.KeywordRequest;
import kong.portfolio.portfolio.dto.KeywordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllKeywords() {
        List<KeywordResponse> response = keywordService.getAllKeywords();
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @GetMapping("/{keywordSeq}")
    public ResponseEntity<ResponseDto> getKeyword(@PathVariable Long keywordSeq) {
        KeywordResponse response = keywordService.getKeyword(keywordSeq);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createKeyword(@Valid @RequestBody KeywordRequest request) {
        KeywordResponse response = keywordService.createKeyword(request);
        return ResponseDto.response(StatusCode.CREATED, response);
    }

    @PutMapping("/{keywordSeq}")
    public ResponseEntity<ResponseDto> updateKeyword(
            @PathVariable Long keywordSeq,
            @Valid @RequestBody KeywordRequest request) {
        KeywordResponse response = keywordService.updateKeyword(keywordSeq, request);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @DeleteMapping("/{keywordSeq}")
    public ResponseEntity<ResponseDto> deleteKeyword(@PathVariable Long keywordSeq) {
        keywordService.deleteKeyword(keywordSeq);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/{keywordSeq}/order")
    public ResponseEntity<ResponseDto> updateKeywordOrder(
            @PathVariable Long keywordSeq,
            @RequestParam Integer newOrder) {
        keywordService.updateKeywordOrder(keywordSeq, newOrder);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/order")
    public ResponseEntity<ResponseDto> updateKeywordsOrder(@RequestBody List<Long> keywordSeqs) {
        keywordService.updateKeywordsOrder(keywordSeqs);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }
}
