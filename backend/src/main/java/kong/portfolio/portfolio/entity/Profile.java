package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileSeq;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, length = 50)
    private String nameEn;
    
    @Column(nullable = false)
    private LocalDate birthDate;
    
    @Column(length = 10)
    private String gender;
    
    @Column(length = 20)
    private String phone;
    
    @Column(nullable = false, length = 100)
    private String email;
    
    @Column(length = 255)
    private String github;
    
    @Column(length = 500)
    private String profileImageUrl;
    
    @Column(length = 200)
    private String subtitle;
    
    @Column(columnDefinition = "TEXT")
    private String introduction;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // 프로필 정보 수정
    public void updateProfile(String name, String nameEn, LocalDate birthDate, String gender,
                             String phone, String email, String github, String subtitle, String introduction) {
        this.name = name;
        this.nameEn = nameEn;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.github = github;
        this.subtitle = subtitle;
        this.introduction = introduction;
    }
    
    // 프로필 이미지 변경
    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}