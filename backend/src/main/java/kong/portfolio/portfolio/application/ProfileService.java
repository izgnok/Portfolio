package kong.portfolio.portfolio.application;

import kong.portfolio.portfolio.dto.ProfileRequest;
import kong.portfolio.portfolio.dto.ProfileResponse;
import kong.portfolio.portfolio.entity.Profile;
import kong.portfolio.portfolio.infrastructure.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {
    
    private final ProfileRepository profileRepository;
    
    /**
     * 프로필 조회 (항상 1개만 존재)
     */
    public ProfileResponse getProfile() {
        Profile profile = profileRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("프로필이 존재하지 않습니다."));
        return ProfileResponse.from(profile);
    }
    
    /**
     * 프로필 저장/수정 (항상 같은 row 업데이트)
     */
    @Transactional
    public ProfileResponse saveProfile(ProfileRequest request, MultipartFile profileImage) {
        // 기존 프로필 찾기 (없으면 새로 생성)
        Profile profile = profileRepository.findFirstByOrderByIdAsc()
                .orElse(Profile.builder().build());
        
        // 기본 정보 업데이트
        profile.update(
            request.getName(),
            request.getNameEn(),
            request.getBirthDate(),
            request.getPhone(),
            request.getEmail(),
            request.getGithub()
        );
        
        // 이미지 처리 (제공된 경우만)
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                profile.updateImage(
                    profileImage.getBytes(),
                    profileImage.getContentType()
                );
            } catch (IOException e) {
                throw new RuntimeException("이미지 처리 중 오류가 발생했습니다.", e);
            }
        }
        
        Profile saved = profileRepository.save(profile);
        return ProfileResponse.from(saved);
    }
}
