package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 프로필 Repository (Hard Delete)
 * 프로필은 단일 레코드만 존재
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * 첫 번째 프로필 조회 (단일 레코드)
     */
    Optional<Profile> findFirstByOrderByProfileSeqAsc();
}