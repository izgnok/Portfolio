package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_tech_stacks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProjectTechStack {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectTechStackSeq;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_seq", nullable = false)
    private Project project;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_seq", nullable = false)
    private Skill skill;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    // 양방향 관계 설정
    protected void setProject(Project project) {
        this.project = project;
    }
}