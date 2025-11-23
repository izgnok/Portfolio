package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 프로젝트 이미지 Repository (Hard Delete)
 */
@Repository
public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {

    /**
     * 프로젝트의 모든 이미지 조회 (순서대로)
     */
    List<ProjectImage> findAllByProject_ProjectSeqOrderByDisplayOrderAsc(Long projectSeq);

    /**
     * 프로젝트의 이미지 개수
     */
    long countByProject_ProjectSeq(Long projectSeq);
}