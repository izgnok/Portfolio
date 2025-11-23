package kong.portfolio.portfolio.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Certificate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateSeq;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 100)
    private String issuer;
    
    @Column
    private LocalDate issueDate;
    
    @Column(length = 100)
    private String credentialId;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // 자격증 정보 수정
    public void updateCertificate(String name, String issuer, LocalDate issueDate, 
                                 String credentialId, String description) {
        this.name = name;
        this.issuer = issuer;
        this.issueDate = issueDate;
        this.credentialId = credentialId;
        this.description = description;
    }
    
    // 순서 변경
    public void updateDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}