package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    
    /**
     * 카테고리순, 이름순 정렬 조회
     */
    List<Skill> findAllByOrderByCategoryAscNameAsc();
}
