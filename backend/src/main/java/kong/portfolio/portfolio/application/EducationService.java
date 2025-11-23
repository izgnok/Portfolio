package kong.portfolio.portfolio.application;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.dto.EducationRequest;
import kong.portfolio.portfolio.dto.EducationResponse;
import kong.portfolio.portfolio.entity.Education;
import kong.portfolio.portfolio.infrastructure.EducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 학력 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EducationService {

    private final EducationRepository educationRepository;

    /**
     * 모든 학력 조회
     */
    public List<EducationResponse> getAllEducations() {
        return educationRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(EducationResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 학력 단건 조회
     */
    public EducationResponse getEducation(Long educationSeq) {
        Education education = educationRepository.findById(educationSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.EDUCATION_NOT_FOUND));
        
        return EducationResponse.from(education);
    }

    /**
     * 학력 생성
     */
    @Transactional
    public EducationResponse createEducation(EducationRequest request) {
        // 다음 순서 계산
        int nextOrder = (int) educationRepository.count();

        Education education = Education.builder()
                .institution(request.getInstitution())
                .major(request.getMajor())
                .degree(request.getDegree())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .gpa(request.getGpa())
                .description(request.getDescription())
                .displayOrder(nextOrder)
                .build();

        Education savedEducation = educationRepository.save(education);
        log.info("학력 생성 완료: {}", savedEducation.getEducationSeq());
        
        return EducationResponse.from(savedEducation);
    }

    /**
     * 학력 수정
     */
    @Transactional
    public EducationResponse updateEducation(Long educationSeq, EducationRequest request) {
        Education education = educationRepository.findById(educationSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.EDUCATION_NOT_FOUND));

        education.updateEducation(
                request.getInstitution(),
                request.getMajor(),
                request.getDegree(),
                request.getStartDate(),
                request.getEndDate(),
                request.getGpa(),
                request.getDescription()
        );

        log.info("학력 수정 완료: {}", educationSeq);
        return EducationResponse.from(education);
    }

    /**
     * 학력 삭제
     */
    @Transactional
    public void deleteEducation(Long educationSeq) {
        Education education = educationRepository.findById(educationSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.EDUCATION_NOT_FOUND));

        educationRepository.delete(education);
        log.info("학력 삭제 완료: {}", educationSeq);
    }

    /**
     * 학력 순서 변경
     */
    @Transactional
    public void updateEducationOrder(Long educationSeq, Integer newOrder) {
        Education education = educationRepository.findById(educationSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.EDUCATION_NOT_FOUND));

        education.updateDisplayOrder(newOrder);
        log.info("학력 순서 변경 완료: {} -> {}", educationSeq, newOrder);
    }

    /**
     * 학력 일괄 순서 변경
     */
    @Transactional
    public void updateEducationsOrder(List<Long> educationSeqs) {
        for (int i = 0; i < educationSeqs.size(); i++) {
            Long educationSeq = educationSeqs.get(i);
            Education education = educationRepository.findById(educationSeq)
                    .orElseThrow(() -> new RestApiException(StatusCode.EDUCATION_NOT_FOUND));
            
            education.updateDisplayOrder(i);
        }
        
        log.info("학력 일괄 순서 변경 완료: {} 건", educationSeqs.size());
    }
}
