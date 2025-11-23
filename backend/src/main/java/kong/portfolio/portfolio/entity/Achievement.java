package kong.portfolio.portfolio.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "achievements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Achievement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achievementSeq;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_seq", nullable = false)
    private Project project;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String problem;
    
    @Column(columnDefinition = "TEXT")
    private String solution;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // 양방향 관계 설정
    protected void setProject(Project project) {
        this.project = project;
    }
    
    // 성과/문제해결 수정
    public void updateAchievement(String title, String problem, String solution) {
        this.title = title;
        this.problem = problem;
        this.solution = solution;
    }
    
    // 순서 변경
    public void updateDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}