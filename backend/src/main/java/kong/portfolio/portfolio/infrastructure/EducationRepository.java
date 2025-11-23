package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    
    /**
     * 시작일 기준 최신순 조회
     */
    List<Education> findAllByOrderByStartDateDesc();
}
