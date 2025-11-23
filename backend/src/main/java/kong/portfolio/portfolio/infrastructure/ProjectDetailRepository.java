package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.ProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 프로젝트 상세 Repository
 */
@Repository
public interface ProjectDetailRepository extends JpaRepository<ProjectDetail, Long> {

    /**
     * 프로젝트 ID로 상세 정보 조회
     */
    Optional<ProjectDetail> findByProject_ProjectSeq(Long projectSeq);
}