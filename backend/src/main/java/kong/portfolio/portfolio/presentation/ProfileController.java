package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.ProfileService;
import kong.portfolio.portfolio.dto.ProfileRequest;
import kong.portfolio.portfolio.dto.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ResponseDto> getProfile() {
        ProfileResponse response = profileService.getProfile();
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createProfile(@Valid @RequestBody ProfileRequest request) {
        ProfileResponse response = profileService.createProfile(request);
        return ResponseDto.response(StatusCode.CREATED, response);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateProfile(@Valid @RequestBody ProfileRequest request) {
        ProfileResponse response = profileService.updateProfile(request);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PostMapping("/image")
    public ResponseEntity<ResponseDto> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = profileService.uploadProfileImage(file);
        return ResponseDto.response(StatusCode.SUCCESS, imageUrl);
    }

    @DeleteMapping("/image")
    public ResponseEntity<ResponseDto> deleteProfileImage() {
        profileService.deleteProfileImage();
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }
}
