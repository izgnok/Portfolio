package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 학력 Repository
 */
@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    /**
     * 삭제되지 않은 모든 학력 조회 (순서대로)
     */
    List<Education> findAllByDeletedFalseOrderByDisplayOrderAsc();

    /**
     * 삭제되지 않은 학력 조회 (ID로)
     */
    Optional<Education> findByEducationSeqAndDeletedFalse(Long educationSeq);

    /**
     * 삭제되지 않은 학력 개수
     */
    long countByDeletedFalse();
}