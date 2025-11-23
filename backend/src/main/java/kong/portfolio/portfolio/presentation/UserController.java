package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.UserService;
import kong.portfolio.portfolio.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 로그인 (세션 생성)
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session) {
        log.info("로그인 시도: {}", request.getUsername());
        userService.login(request, session);
        log.info("로그인 성공, 세션 ID: {}", session.getId());
        return ResponseDto.response(StatusCode.SUCCESS, "로그인 성공");
    }

    /**
     * 로그아웃 (세션 무효화)
     */
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(HttpSession session) {
        session.invalidate();
        log.info("로그아웃 및 세션 무효화 완료");
        return ResponseDto.response(StatusCode.SUCCESS, "로그아웃 성공");
    }
}
