package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 학력 Repository (Hard Delete)
 */
@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    /**
     * 모든 학력 조회 (순서대로)
     */
    List<Education> findAllByOrderByDisplayOrderAsc();
}