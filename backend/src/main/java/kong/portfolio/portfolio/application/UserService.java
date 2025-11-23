package kong.portfolio.portfolio.application;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.dto.UserRequest;
import kong.portfolio.portfolio.dto.UserResponse;
import kong.portfolio.portfolio.entity.User;
import kong.portfolio.portfolio.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 조회 (username)
     */
    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RestApiException(StatusCode.USER_NOT_FOUND));
        
        return UserResponse.from(user);
    }

    /**
     * 사용자 생성 (회원가입)
     */
    @Transactional
    public UserResponse createUser(UserRequest request) {
        // username 중복 체크
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RestApiException(StatusCode.USER_ALREADY_EXISTS);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .role("ADMIN") // 기본 역할
                .build();

        User savedUser = userRepository.save(user);
        log.info("사용자 생성 완료: {}", savedUser.getUsername());
        
        return UserResponse.from(savedUser);
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RestApiException(StatusCode.USER_NOT_FOUND));

        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RestApiException(StatusCode.INVALID_PASSWORD);
        }

        // 새 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.changePassword(encodedPassword);

        log.info("비밀번호 변경 완료: {}", username);
    }

    /**
     * 로그인 검증
     */
    public User validateLogin(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RestApiException(StatusCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RestApiException(StatusCode.INVALID_PASSWORD);
        }

        log.info("로그인 성공: {}", username);
        return user;
    }

    /**
     * 사용자 삭제
     */
    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RestApiException(StatusCode.USER_NOT_FOUND));

        userRepository.delete(user);
        log.info("사용자 삭제 완료: {}", username);
    }
}
