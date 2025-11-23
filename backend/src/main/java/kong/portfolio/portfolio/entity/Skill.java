package kong.portfolio.portfolio.entity;


import jakarta.persistence.*;
import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "skills")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillSeq;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 50)
    private String category;
    
    @Column(length = 10)
    private String icon;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer level = 1;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // 스킬 정보 수정
    public void updateSkill(String name, String category, String icon, Integer level) {
        this.name = name;
        this.category = category;
        this.icon = icon;
        this.level = level;
    }
    
    // 레벨만 변경
    public void updateLevel(Integer level) {
        if (level == null || level < 1 || level > 5) {
            throw new RestApiException(StatusCode.INVALID_INPUT_VALUE, "레벨은 1~5 사이여야 합니다.");
        }
        this.level = level;
    }
    
    // 순서 변경
    public void updateDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}