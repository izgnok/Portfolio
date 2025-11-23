package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 성과 Repository (Hard Delete)
 */
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    /**
     * 프로젝트의 모든 성과 조회 (순서대로)
     */
    List<Achievement> findAllByProject_ProjectSeqOrderByDisplayOrderAsc(Long projectSeq);

    /**
     * 프로젝트의 성과 개수
     */
    long countByProject_ProjectSeq(Long projectSeq);
}