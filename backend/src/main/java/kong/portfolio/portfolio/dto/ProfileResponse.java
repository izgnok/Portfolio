package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    
    private Long id;
    private String name;
    private String nameEn;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String github;
    private String profileImage;  // Base64 인코딩된 이미지 (data:image/jpeg;base64,...)
    
    public static ProfileResponse from(Profile profile) {
        String imageData = null;
        if (profile.getProfileImage() != null) {
            String base64 = Base64.getEncoder().encodeToString(profile.getProfileImage());
            imageData = "data:" + profile.getProfileImageType() + ";base64," + base64;
        }
        
        return ProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getName())
                .nameEn(profile.getNameEn())
                .birthDate(profile.getBirthDate())
                .phone(profile.getPhone())
                .email(profile.getEmail())
                .github(profile.getGithub())
                .profileImage(imageData)
                .build();
    }
}
