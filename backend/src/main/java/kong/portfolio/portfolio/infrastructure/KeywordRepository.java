package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    
    /**
     * displayOrder 순으로 전체 조회
     */
    List<Keyword> findAllByOrderByDisplayOrderAsc();
}
