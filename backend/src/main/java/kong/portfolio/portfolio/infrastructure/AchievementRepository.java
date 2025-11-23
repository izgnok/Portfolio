package kong.portfolio.portfolio.infrastructure;


import kong.portfolio.portfolio.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 성과 Repository
 */
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    /**
     * 프로젝트의 모든 성과 조회 (순서대로)
     */
    List<Achievement> findAllByProject_ProjectSeqAndDeletedFalseOrderByDisplayOrderAsc(Long projectSeq);

    /**
     * 삭제되지 않은 성과 조회 (ID로)
     */
    Optional<Achievement> findByAchievementSeqAndDeletedFalse(Long achievementSeq);

    /**
     * 프로젝트의 성과 개수
     */
    long countByProject_ProjectSeqAndDeletedFalse(Long projectSeq);
}