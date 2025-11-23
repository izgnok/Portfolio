package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    /**
     * 첫 번째 프로필 조회 (항상 1개만 존재)
     */
    Optional<Profile> findFirstByOrderByIdAsc();
}
