package kong.portfolio.portfolio.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectSeq;
    
    @Column(nullable = false, length = 100)
    private String title;
    
    @Column(length = 255)
    private String subtitle;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Column(nullable = false)
    private LocalDate endDate;
    
    @Column
    private Integer teamSize;
    
    @Column(length = 50)
    private String projectType;
    
    @Column(length = 50)
    private String award;
    
    @Column(length = 500)
    private String githubUrl;
    
    @Column(length = 500)
    private String demoUrl;
    
    @Column(length = 10)
    private String icon;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // 프로젝트 상세 (1:1)
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ProjectDetail projectDetail;
    
    // 프로젝트 이미지 (1:N)
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectImage> projectImages = new ArrayList<>();
    
    // 프로젝트 기술스택 (N:M)
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectTechStack> projectTechStacks = new ArrayList<>();
    
    // 성과/문제해결 (1:N)
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Achievement> achievements = new ArrayList<>();
    
    // 프로젝트 기본 정보 수정
    public void updateProject(String title, String subtitle, LocalDate startDate, LocalDate endDate,
                             Integer teamSize, String projectType, String award, 
                             String githubUrl, String demoUrl, String icon) {
        this.title = title;
        this.subtitle = subtitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamSize = teamSize;
        this.projectType = projectType;
        this.award = award;
        this.githubUrl = githubUrl;
        this.demoUrl = demoUrl;
        this.icon = icon;
    }
    
    // 프로젝트 상세 설정
    public void setProjectDetail(ProjectDetail projectDetail) {
        this.projectDetail = projectDetail;
        projectDetail.setProject(this);
    }
    
    // 이미지 추가
    public void addProjectImage(ProjectImage projectImage) {
        this.projectImages.add(projectImage);
        projectImage.setProject(this);
    }
    
    // 이미지 제거
    public void removeProjectImage(ProjectImage projectImage) {
        this.projectImages.remove(projectImage);
    }
    
    // 기술스택 추가
    public void addTechStack(ProjectTechStack projectTechStack) {
        this.projectTechStacks.add(projectTechStack);
        projectTechStack.setProject(this);
    }
    
    // 기술스택 제거
    public void removeTechStack(ProjectTechStack projectTechStack) {
        this.projectTechStacks.remove(projectTechStack);
    }
    
    // 성과 추가
    public void addAchievement(Achievement achievement) {
        this.achievements.add(achievement);
        achievement.setProject(this);
    }
    
    // 성과 제거
    public void removeAchievement(Achievement achievement) {
        this.achievements.remove(achievement);
    }
    
    // 순서 변경
    public void updateDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}