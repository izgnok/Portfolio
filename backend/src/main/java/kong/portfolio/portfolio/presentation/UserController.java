package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.UserService;
import kong.portfolio.portfolio.dto.PasswordChangeRequest;
import kong.portfolio.portfolio.dto.UserRequest;
import kong.portfolio.portfolio.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable String username) {
        UserResponse response = userService.getUser(username);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseDto.response(StatusCode.CREATED, response);
    }

    @PatchMapping("/{username}/password")
    public ResponseEntity<ResponseDto> changePassword(
            @PathVariable String username,
            @Valid @RequestBody PasswordChangeRequest request) {
        userService.changePassword(username, request.getOldPassword(), request.getNewPassword());
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }
}
