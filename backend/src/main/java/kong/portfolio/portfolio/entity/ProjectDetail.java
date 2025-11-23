package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProjectDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectDetailSeq;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_seq", nullable = false)
    private Project project;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    @Column(columnDefinition = "TEXT")
    private String coreValues;
    
    @Column(columnDefinition = "TEXT")
    private String mainFeatures;
    
    @Column(columnDefinition = "TEXT")
    private String myRole;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // 양방향 관계 설정
    protected void setProject(Project project) {
        this.project = project;
    }
    
    // 프로젝트 상세 정보 수정
    public void updateProjectDetail(String summary, String coreValues, 
                                   String mainFeatures, String myRole) {
        this.summary = summary;
        this.coreValues = coreValues;
        this.mainFeatures = mainFeatures;
        this.myRole = myRole;
    }
}