package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 프로필 Repository
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * 삭제되지 않은 첫 번째 프로필 조회 (단일 레코드)
     */
    Optional<Profile> findFirstByDeletedFalse();
}