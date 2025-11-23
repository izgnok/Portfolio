package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 자격증 Repository
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    /**
     * 삭제되지 않은 모든 자격증 조회 (순서대로)
     */
    List<Certificate> findAllByDeletedFalseOrderByDisplayOrderAsc();

    /**
     * 삭제되지 않은 자격증 조회 (ID로)
     */
    Optional<Certificate> findByCertificateSeqAndDeletedFalse(Long certificateSeq);

    /**
     * 삭제되지 않은 자격증 개수
     */
    long countByDeletedFalse();
}