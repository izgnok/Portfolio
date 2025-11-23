package kong.portfolio.portfolio.application;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.dto.ProfileRequest;
import kong.portfolio.portfolio.dto.ProfileResponse;
import kong.portfolio.portfolio.entity.Profile;
import kong.portfolio.portfolio.infrastructure.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 프로필 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    // private final FileService fileService; // 파일 업로드 서비스 (추후 구현)

    /**
     * 프로필 조회 (단일 레코드)
     */
    public ProfileResponse getProfile() {
        Profile profile = profileRepository.findFirstByOrderByProfileSeqAsc()
                .orElseThrow(() -> new RestApiException(StatusCode.PROFILE_NOT_FOUND));
        
        return ProfileResponse.from(profile);
    }

    /**
     * 프로필 생성 (최초 1회만)
     */
    @Transactional
    public ProfileResponse createProfile(ProfileRequest request) {
        // 이미 프로필이 존재하면 예외
        if (profileRepository.count() > 0) {
            throw new RestApiException(StatusCode.PROFILE_ALREADY_EXISTS);
        }

        Profile profile = Profile.builder()
                .name(request.getName())
                .nameEn(request.getNameEn())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .phone(request.getPhone())
                .email(request.getEmail())
                .github(request.getGithub())
                .subtitle(request.getSubtitle())
                .introduction(request.getIntroduction())
                .build();

        Profile savedProfile = profileRepository.save(profile);
        log.info("프로필 생성 완료: {}", savedProfile.getProfileSeq());
        
        return ProfileResponse.from(savedProfile);
    }

    /**
     * 프로필 수정
     */
    @Transactional
    public ProfileResponse updateProfile(ProfileRequest request) {
        Profile profile = profileRepository.findFirstByOrderByProfileSeqAsc()
                .orElseThrow(() -> new RestApiException(StatusCode.PROFILE_NOT_FOUND));

        profile.updateProfile(
                request.getName(),
                request.getNameEn(),
                request.getBirthDate(),
                request.getGender(),
                request.getPhone(),
                request.getEmail(),
                request.getGithub(),
                request.getSubtitle(),
                request.getIntroduction()
        );

        log.info("프로필 수정 완료: {}", profile.getProfileSeq());
        return ProfileResponse.from(profile);
    }

    /**
     * 프로필 이미지 업로드
     */
    @Transactional
    public String uploadProfileImage(MultipartFile file) {
        Profile profile = profileRepository.findFirstByOrderByProfileSeqAsc()
                .orElseThrow(() -> new RestApiException(StatusCode.PROFILE_NOT_FOUND));

        // TODO: 파일 업로드 서비스 구현 필요
        // String imageUrl = fileService.uploadFile(file, "profile");
        String imageUrl = "/uploads/profile/" + file.getOriginalFilename(); // 임시

        profile.updateProfileImage(imageUrl);
        
        log.info("프로필 이미지 업로드 완료: {}", imageUrl);
        return imageUrl;
    }

    /**
     * 프로필 이미지 삭제
     */
    @Transactional
    public void deleteProfileImage() {
        Profile profile = profileRepository.findFirstByOrderByProfileSeqAsc()
                .orElseThrow(() -> new RestApiException(StatusCode.PROFILE_NOT_FOUND));

        String oldImageUrl = profile.getProfileImageUrl();
        profile.updateProfileImage(null);

        // TODO: 실제 파일 삭제
        // fileService.deleteFile(oldImageUrl);
        
        log.info("프로필 이미지 삭제 완료: {}", oldImageUrl);
    }
}
