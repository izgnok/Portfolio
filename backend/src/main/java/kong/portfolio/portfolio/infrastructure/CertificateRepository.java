package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 자격증 Repository (Hard Delete)
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    /**
     * 모든 자격증 조회 (순서대로)
     */
    List<Certificate> findAllByOrderByDisplayOrderAsc();
}