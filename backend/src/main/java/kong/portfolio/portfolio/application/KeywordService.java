package kong.portfolio.portfolio.application;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.dto.KeywordRequest;
import kong.portfolio.portfolio.dto.KeywordResponse;
import kong.portfolio.portfolio.entity.Keyword;
import kong.portfolio.portfolio.infrastructure.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 키워드 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordService {

    private final KeywordRepository keywordRepository;

    /**
     * 모든 키워드 조회
     */
    public List<KeywordResponse> getAllKeywords() {
        return keywordRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(KeywordResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 키워드 단건 조회
     */
    public KeywordResponse getKeyword(Long keywordSeq) {
        Keyword keyword = keywordRepository.findById(keywordSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.KEYWORD_NOT_FOUND));
        
        return KeywordResponse.from(keyword);
    }

    /**
     * 키워드 생성
     */
    @Transactional
    public KeywordResponse createKeyword(KeywordRequest request) {
        // 다음 순서 계산
        int nextOrder = (int) keywordRepository.count();

        Keyword keyword = Keyword.builder()
                .content(request.getContent())
                .displayOrder(nextOrder)
                .build();

        Keyword savedKeyword = keywordRepository.save(keyword);
        log.info("키워드 생성 완료: {}", savedKeyword.getKeywordSeq());
        
        return KeywordResponse.from(savedKeyword);
    }

    /**
     * 키워드 수정
     */
    @Transactional
    public KeywordResponse updateKeyword(Long keywordSeq, KeywordRequest request) {
        Keyword keyword = keywordRepository.findById(keywordSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.KEYWORD_NOT_FOUND));

        keyword.updateContent(request.getContent());
        
        log.info("키워드 수정 완료: {}", keywordSeq);
        return KeywordResponse.from(keyword);
    }

    /**
     * 키워드 삭제
     */
    @Transactional
    public void deleteKeyword(Long keywordSeq) {
        Keyword keyword = keywordRepository.findById(keywordSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.KEYWORD_NOT_FOUND));

        keywordRepository.delete(keyword);
        log.info("키워드 삭제 완료: {}", keywordSeq);
    }

    /**
     * 키워드 순서 변경
     */
    @Transactional
    public void updateKeywordOrder(Long keywordSeq, Integer newOrder) {
        Keyword keyword = keywordRepository.findById(keywordSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.KEYWORD_NOT_FOUND));

        keyword.updateDisplayOrder(newOrder);
        log.info("키워드 순서 변경 완료: {} -> {}", keywordSeq, newOrder);
    }

    /**
     * 키워드 일괄 순서 변경
     */
    @Transactional
    public void updateKeywordsOrder(List<Long> keywordSeqs) {
        for (int i = 0; i < keywordSeqs.size(); i++) {
            Long keywordSeq = keywordSeqs.get(i);
            Keyword keyword = keywordRepository.findById(keywordSeq)
                    .orElseThrow(() -> new RestApiException(StatusCode.KEYWORD_NOT_FOUND));
            
            keyword.updateDisplayOrder(i);
        }
        
        log.info("키워드 일괄 순서 변경 완료: {} 건", keywordSeqs.size());
    }
}
