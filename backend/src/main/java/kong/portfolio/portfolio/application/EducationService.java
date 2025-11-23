package kong.portfolio.portfolio.application;

import kong.portfolio.portfolio.dto.EducationRequest;
import kong.portfolio.portfolio.dto.EducationResponse;
import kong.portfolio.portfolio.entity.Education;
import kong.portfolio.portfolio.infrastructure.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EducationService {

    private final EducationRepository educationRepository;

    /**
     * 학력 목록 조회 (최신순)
     */
    public List<EducationResponse> getEducations() {
        return educationRepository.findAllByOrderByStartDateDesc()
                .stream()
                .map(EducationResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 학력 저장
     */
    @Transactional
    public EducationResponse createEducation(EducationRequest request) {
        Education education = Education.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .gpa(request.getGpa())
                .build();

        Education saved = educationRepository.save(education);
        return EducationResponse.from(saved);
    }

    /**
     * 학력 수정 (추가)
     */
    @Transactional
    public EducationResponse updateEducation(Long id, EducationRequest request) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("학력을 찾을 수 없습니다."));

        education.update(
                request.getName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getStatus(),
                request.getGpa()
        );

        return EducationResponse.from(education);
    }

    /**
     * 학력 삭제
     */
    @Transactional
    public void deleteEducation(Long id) {
        if (!educationRepository.existsById(id)) {
            throw new RuntimeException("학력을 찾을 수 없습니다.");
        }
        educationRepository.deleteById(id);
    }
}
