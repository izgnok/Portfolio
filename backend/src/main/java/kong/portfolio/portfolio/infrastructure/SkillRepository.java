package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 기술스택 Repository
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    /**
     * 삭제되지 않은 모든 기술스택 조회 (순서대로)
     */
    List<Skill> findAllByDeletedFalseOrderByDisplayOrderAsc();

    /**
     * 카테고리별 기술스택 조회
     */
    List<Skill> findAllByCategoryAndDeletedFalseOrderByDisplayOrderAsc(String category);

    /**
     * 삭제되지 않은 기술스택 조회 (ID로)
     */
    Optional<Skill> findBySkillSeqAndDeletedFalse(Long skillSeq);

    /**
     * 삭제되지 않은 기술스택 개수
     */
    long countByDeletedFalse();

    /**
     * 카테고리별 기술스택 개수
     */
    long countByCategoryAndDeletedFalse(String category);
}