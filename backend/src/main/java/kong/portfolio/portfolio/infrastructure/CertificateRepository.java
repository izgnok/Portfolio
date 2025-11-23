package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    
    /**
     * 취득일자 기준 최신순 조회
     */
    List<Certificate> findAllByOrderByIssueDateDesc();
}
