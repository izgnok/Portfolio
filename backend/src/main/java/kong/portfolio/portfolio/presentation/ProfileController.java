package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.ProfileService;
import kong.portfolio.portfolio.dto.ProfileRequest;
import kong.portfolio.portfolio.dto.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    
    private final ProfileService profileService;
    
    /**
     * 프로필 조회
     */
    @GetMapping
    public ResponseEntity<ResponseDto> getProfile() {
        ProfileResponse profile = profileService.getProfile();
        return ResponseDto.response(StatusCode.SUCCESS, profile);
    }
    
    /**
     * 프로필 저장/수정 (한 번에 처리)
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> saveProfile(
            @RequestPart("profile") ProfileRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        ProfileResponse profile = profileService.saveProfile(request, profileImage);
        return ResponseDto.response(StatusCode.SUCCESS, profile);
    }
}
