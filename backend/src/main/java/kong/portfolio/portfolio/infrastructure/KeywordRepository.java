package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 키워드 Repository
 */
@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    /**
     * 삭제되지 않은 모든 키워드 조회 (순서대로)
     */
    List<Keyword> findAllByDeletedFalseOrderByDisplayOrderAsc();

    /**
     * 삭제되지 않은 키워드 개수
     */
    long countByDeletedFalse();
}