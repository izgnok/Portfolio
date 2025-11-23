package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.ProjectTechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 프로젝트-기술스택 Repository
 */
@Repository
public interface ProjectTechStackRepository extends JpaRepository<ProjectTechStack, Long> {

    /**
     * 프로젝트의 모든 기술스택 조회
     */
    @Query("SELECT pts FROM ProjectTechStack pts " +
           "JOIN FETCH pts.skill " +
           "WHERE pts.project.projectSeq = :projectSeq AND pts.deleted = false " +
           "ORDER BY pts.skill.displayOrder ASC")
    List<ProjectTechStack> findAllByProjectSeqWithSkill(Long projectSeq);

    /**
     * 기술스택이 사용된 프로젝트 조회
     */
    @Query("SELECT pts FROM ProjectTechStack pts " +
           "JOIN FETCH pts.project " +
           "WHERE pts.skill.skillSeq = :skillSeq AND pts.deleted = false")
    List<ProjectTechStack> findAllBySkillSeqWithProject(Long skillSeq);

    /**
     * 프로젝트-기술스택 연결 존재 여부
     */
    boolean existsByProject_ProjectSeqAndSkill_SkillSeqAndDeletedFalse(Long projectSeq, Long skillSeq);

    /**
     * 프로젝트-기술스택 연결 조회
     */
    Optional<ProjectTechStack> findByProject_ProjectSeqAndSkill_SkillSeqAndDeletedFalse(Long projectSeq, Long skillSeq);

    /**
     * 프로젝트의 기술스택 개수
     */
    long countByProject_ProjectSeqAndDeletedFalse(Long projectSeq);
}