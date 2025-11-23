package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skill")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;  // 스킬명 (예: Spring Boot, React, MySQL)
    
    @Column(nullable = false)
    private Integer level;  // 숙련도 0~5
    
    @Column(nullable = false, length = 50)
    private String category;  // 프론트엔드, 백엔드, CI/CD, DB, 기타
    
    // 수정 메서드
    public void update(String name, Integer level, String category) {
        this.name = name;
        this.level = level;
        this.category = category;
    }
}
