package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 기술스택 Repository (Hard Delete)
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    /**
     * 모든 기술스택 조회 (순서대로)
     */
    List<Skill> findAllByOrderByDisplayOrderAsc();

    /**
     * 카테고리별 기술스택 조회
     */
    List<Skill> findAllByCategoryOrderByDisplayOrderAsc(String category);

    /**
     * 카테고리별 기술스택 개수
     */
    long countByCategory(String category);
}