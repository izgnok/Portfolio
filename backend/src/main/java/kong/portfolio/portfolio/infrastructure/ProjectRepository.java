package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * 시작일 기준 최신순 조회
     */
    List<Project> findAllByOrderByStartDateDesc();

    /**
     * 수상작만 조회 (hasAward = true)
     */
    List<Project> findByHasAwardTrueOrderByStartDateDesc();
}
