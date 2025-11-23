package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String nameEn;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String github;

    // 👇 이미지 MySQL BLOB으로 저장
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] profileImage;

    @Column(length = 100)
    private String profileImageType;  // image/jpeg, image/png

    // 저장/수정 메서드
    public void update(String name, String nameEn, LocalDate birthDate,
                       String phone, String email, String github) {
        this.name = name;
        this.nameEn = nameEn;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.github = github;
    }

    public void updateImage(byte[] image, String imageType) {
        this.profileImage = image;
        this.profileImageType = imageType;
    }
}
