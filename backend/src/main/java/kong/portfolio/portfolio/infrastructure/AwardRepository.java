package kong.portfolio.portfolio.infrastructure;


import kong.portfolio.portfolio.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 수상 Repository
 */
@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {

    /**
     * 삭제되지 않은 모든 수상 조회 (순서대로)
     */
    List<Award> findAllByDeletedFalseOrderByDisplayOrderAsc();

    /**
     * 삭제되지 않은 수상 조회 (ID로)
     */
    Optional<Award> findByAwardSeqAndDeletedFalse(Long awardSeq);

    /**
     * 삭제되지 않은 수상 개수
     */
    long countByDeletedFalse();

    Collection<Award> findAllByOrderByDisplayOrderAsc();
}