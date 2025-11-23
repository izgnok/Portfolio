package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitor_logs", indexes = {
    @Index(name = "idx_visited_at", columnList = "visitedAt"),
    @Index(name = "idx_page_url", columnList = "pageUrl")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class VisitorLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitorLogSeq;
    
    @Column(length = 50)
    private String ipAddress;
    
    @Column(length = 255)
    private String pageUrl;
    
    @Column(length = 100)
    private String device;
    
    @Column(length = 255)
    private String userAgent;
    
    @Column
    private Integer durationSeconds;
    
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime visitedAt;
    
    // 체류시간 업데이트
    public void updateDuration(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}