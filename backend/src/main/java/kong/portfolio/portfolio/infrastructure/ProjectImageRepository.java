package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 프로젝트 이미지 Repository
 */
@Repository
public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {

    /**
     * 프로젝트의 모든 이미지 조회 (순서대로)
     */
    List<ProjectImage> findAllByProject_ProjectSeqAndDeletedFalseOrderByDisplayOrderAsc(Long projectSeq);

    /**
     * 삭제되지 않은 이미지 조회 (ID로)
     */
    Optional<ProjectImage> findByProjectImageSeqAndDeletedFalse(Long projectImageSeq);

    /**
     * 프로젝트의 이미지 개수
     */
    long countByProject_ProjectSeqAndDeletedFalse(Long projectSeq);
}