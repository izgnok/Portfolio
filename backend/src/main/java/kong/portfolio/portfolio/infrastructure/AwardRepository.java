package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 수상 Repository (Hard Delete)
 */
@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {

    /**
     * 모든 수상 조회 (displayOrder 순서대로)
     */
    List<Award> findAllByOrderByDisplayOrderAsc();

    // findById(Long id) - JpaRepository 기본 메서드 사용
    // count() - JpaRepository 기본 메서드 사용
    // delete(Award award) - JpaRepository 기본 메서드 사용
}
