package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 프로필 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

    private Long profileSeq;
    private String name;
    private String nameEn;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String email;
    private String github;
    private String profileImageUrl;
    private String subtitle;
    private String introduction;

    public static ProfileResponse from(Profile profile) {
        return ProfileResponse.builder()
                .profileSeq(profile.getProfileSeq())
                .name(profile.getName())
                .nameEn(profile.getNameEn())
                .birthDate(profile.getBirthDate())
                .gender(profile.getGender())
                .phone(profile.getPhone())
                .email(profile.getEmail())
                .github(profile.getGithub())
                .profileImageUrl(profile.getProfileImageUrl())
                .subtitle(profile.getSubtitle())
                .introduction(profile.getIntroduction())
                .build();
    }
}