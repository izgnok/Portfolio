package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProjectImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectImageSeq;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_seq", nullable = false)
    private Project project;
    
    @Column(nullable = false, length = 500)
    private String imageUrl;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    // 양방향 관계 설정
    protected void setProject(Project project) {
        this.project = project;
    }
    
    // 이미지 URL 변경
    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    // 순서 변경
    public void updateDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}