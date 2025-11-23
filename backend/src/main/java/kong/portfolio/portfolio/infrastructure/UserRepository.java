package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 Repository (Hard Delete)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * username으로 사용자 조회
     */
    Optional<User> findByUsername(String username);

    /**
     * username 존재 여부 확인
     */
    boolean existsByUsername(String username);
}