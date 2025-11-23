package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 기본 정보
    @Column(nullable = false, length = 200)
    private String name;
    
    @Column(nullable = false)
    private Integer teamSize;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Column(nullable = false)
    private LocalDate endDate;
    
    @Column(nullable = false, length = 20)
    private String status;  // 진행중, 완료
    
    // 수상 정보
    @Column(nullable = false)
    private Boolean hasAward;
    
    @Column(length = 100)
    private String awardName;
    
    @Column(length = 100)
    private String awardOrganization;
    
    // 시스템 아키텍처 이미지 (null 가능)
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] architectureImage;
    
    @Column(length = 100)
    private String architectureImageType;
    
    // JSON 데이터들
    @Column(columnDefinition = "JSON")
    private String summaries;  // ["요약1", "요약2"]
    
    @Column(columnDefinition = "JSON")
    private String coreValues;  // ["핵심가치1", "핵심가치2"]
    
    @Column(columnDefinition = "JSON")
    private String mainFeatures;  // ["주요기능1", "주요기능2"]
    
    @Column(columnDefinition = "JSON")
    private String roles;  // ["역할1", "역할2"]
    
    // 기술스택 (카테고리별, null 가능)
    @Column(columnDefinition = "JSON")
    private String techDatabase;
    
    @Column(columnDefinition = "JSON")
    private String techBackend;
    
    @Column(columnDefinition = "JSON")
    private String techFrontend;
    
    @Column(columnDefinition = "JSON")
    private String techIot;
    
    @Column(columnDefinition = "JSON")
    private String techCicd;
    
    @Column(columnDefinition = "JSON")
    private String techExternalApi;
    
    // 문제/해결 [{"problem": "문제1", "solution": "해결1"}] (null 가능)
    @Column(columnDefinition = "JSON")
    private String problemSolutions;
    
    // 성과 ["성과1", "성과2"] (null 가능)
    @Column(columnDefinition = "JSON")
    private String achievements;
    
    // 아쉬운점 ["아쉬운점1", "아쉬운점2"] (null 가능)
    @Column(columnDefinition = "JSON")
    private String regrets;
    
    // 개선방안 ["개선방안1", "개선방안2"] (null 가능)
    @Column(columnDefinition = "JSON")
    private String improvements;
    
    // 프로젝트 이미지들
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectImage> images = new ArrayList<>();
    
    // 수정 메서드
    public void update(String name, Integer teamSize, LocalDate startDate, LocalDate endDate,
                      String status, Boolean hasAward, String awardName, String awardOrganization,
                      String summaries, String coreValues, String mainFeatures, String roles,
                      String techDatabase, String techBackend, String techFrontend,
                      String techIot, String techCicd, String techExternalApi,
                      String problemSolutions, String achievements, 
                      String regrets, String improvements) {
        this.name = name;
        this.teamSize = teamSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.hasAward = hasAward;
        this.awardName = awardName;
        this.awardOrganization = awardOrganization;
        this.summaries = summaries;
        this.coreValues = coreValues;
        this.mainFeatures = mainFeatures;
        this.roles = roles;
        this.techDatabase = techDatabase;
        this.techBackend = techBackend;
        this.techFrontend = techFrontend;
        this.techIot = techIot;
        this.techCicd = techCicd;
        this.techExternalApi = techExternalApi;
        this.problemSolutions = problemSolutions;
        this.achievements = achievements;
        this.regrets = regrets;
        this.improvements = improvements;
    }
    
    public void updateArchitectureImage(byte[] image, String imageType) {
        this.architectureImage = image;
        this.architectureImageType = imageType;
    }
    
    public void addImage(ProjectImage image) {
        this.images.add(image);
        image.setProject(this);
    }
    
    public void clearImages() {
        this.images.clear();
    }
}
