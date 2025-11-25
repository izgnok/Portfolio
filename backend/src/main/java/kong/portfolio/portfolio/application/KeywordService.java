package kong.portfolio.portfolio.application;

import kong.portfolio.portfolio.dto.KeywordOrderUpdateRequest;
import kong.portfolio.portfolio.dto.KeywordRequest;
import kong.portfolio.portfolio.dto.KeywordResponse;
import kong.portfolio.portfolio.entity.Keyword;
import kong.portfolio.portfolio.infrastructure.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordService {

    private final KeywordRepository keywordRepository;

    /**
     * 키워드 목록 조회 (displayOrder 순)
     */
    public List<KeywordResponse> getKeywords() {
        return keywordRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(KeywordResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 키워드 저장
     */
    @Transactional
    public KeywordResponse createKeyword(KeywordRequest request) {
        Keyword keyword = Keyword.builder()
                .keyword(request.getKeyword())
                .displayOrder(request.getDisplayOrder())
                .build();

        Keyword saved = keywordRepository.save(keyword);
        return KeywordResponse.from(saved);
    }

    /**
     * 키워드 삭제
     */
    @Transactional
    public void deleteKeyword(Long id) {
        if (!keywordRepository.existsById(id)) {
            throw new RuntimeException("키워드를 찾을 수 없습니다.");
        }
        keywordRepository.deleteById(id);
    }

    /**
     * 키워드 순서 일괄 변경
     */
    @Transactional
    public void updateKeywordOrder(KeywordOrderUpdateRequest request) {
        for (KeywordOrderUpdateRequest.KeywordOrderItem item : request.getKeywords()) {
            Keyword keyword = keywordRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("키워드를 찾을 수 없습니다: " + item.getId()));

            keyword.updateDisplayOrder(item.getDisplayOrder());
        }
    }

    /**
     * 키워드 수정
     */
    @Transactional
    public void updateKeyword(Long id, String newKeyword) {
        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("키워드를 찾을 수 없습니다"));
        keyword.updateKeyword(newKeyword);
    }
}
