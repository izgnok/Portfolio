package kong.portfolio.portfolio.application;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.dto.AwardRequest;
import kong.portfolio.portfolio.dto.AwardResponse;
import kong.portfolio.portfolio.entity.Award;
import kong.portfolio.portfolio.infrastructure.AwardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 수상 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AwardService {

    private final AwardRepository awardRepository;

    /**
     * 모든 수상 조회
     */
    public List<AwardResponse> getAllAwards() {
        return awardRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(AwardResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 수상 단건 조회
     */
    public AwardResponse getAward(Long awardSeq) {
        Award award = awardRepository.findById(awardSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.AWARD_NOT_FOUND));
        
        return AwardResponse.from(award);
    }

    /**
     * 수상 생성
     */
    @Transactional
    public AwardResponse createAward(AwardRequest request) {
        // 다음 순서 계산
        int nextOrder = (int) awardRepository.count();

        Award award = Award.builder()
                .title(request.getTitle())
                .organization(request.getOrganization())
                .awardDate(request.getAwardDate())
                .rank(request.getRank())
                .description(request.getDescription())
                .displayOrder(nextOrder)
                .build();

        Award savedAward = awardRepository.save(award);
        log.info("수상 생성 완료: {}", savedAward.getAwardSeq());
        
        return AwardResponse.from(savedAward);
    }

    /**
     * 수상 수정
     */
    @Transactional
    public AwardResponse updateAward(Long awardSeq, AwardRequest request) {
        Award award = awardRepository.findById(awardSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.AWARD_NOT_FOUND));

        award.update(
                request.getTitle(),
                request.getOrganization(),
                request.getAwardDate(),
                request.getRank(),
                request.getDescription(),
                null  // displayOrder는 별도 메서드로 변경
        );

        log.info("수상 수정 완료: {}", awardSeq);
        return AwardResponse.from(award);
    }

    /**
     * 수상 삭제
     */
    @Transactional
    public void deleteAward(Long awardSeq) {
        Award award = awardRepository.findById(awardSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.AWARD_NOT_FOUND));

        awardRepository.delete(award);
        log.info("수상 삭제 완료: {}", awardSeq);
    }

    /**
     * 수상 순서 변경
     */
    @Transactional
    public void updateAwardOrder(Long awardSeq, Integer newOrder) {
        Award award = awardRepository.findById(awardSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.AWARD_NOT_FOUND));

        award.updateDisplayOrder(newOrder);
        log.info("수상 순서 변경 완료: {} -> {}", awardSeq, newOrder);
    }

    /**
     * 수상 일괄 순서 변경
     */
    @Transactional
    public void updateAwardsOrder(List<Long> awardSeqs) {
        for (int i = 0; i < awardSeqs.size(); i++) {
            Long awardSeq = awardSeqs.get(i);
            Award award = awardRepository.findById(awardSeq)
                    .orElseThrow(() -> new RestApiException(StatusCode.AWARD_NOT_FOUND));
            
            award.updateDisplayOrder(i);
        }
        
        log.info("수상 일괄 순서 변경 완료: {} 건", awardSeqs.size());
    }
}
