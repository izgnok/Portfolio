package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "certificate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;  // 자격증명

    @Column(nullable = false, length = 100)
    private String issuer;  // 발급기관

    @Column(length = 100)
    private String certificateNumber;  // 자격번호

    @Column(nullable = false)
    private LocalDate issueDate;  // 취득일자

    // 수정 메서드
    public void update(String name, String issuer, String certificateNumber, LocalDate issueDate) {
        this.name = name;
        this.issuer = issuer;
        this.certificateNumber = certificateNumber;
        this.issueDate = issueDate;
    }
}
