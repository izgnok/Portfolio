package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 프로젝트 Repository (Hard Delete)
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * 모든 프로젝트 조회 (순서대로)
     */
    List<Project> findAllByOrderByDisplayOrderAsc();
}