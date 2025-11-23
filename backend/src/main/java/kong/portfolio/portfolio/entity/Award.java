package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 수상 엔티티
 */
@Entity
@Table(name = "awards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "award_seq")
    private Long awardSeq;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String organization;

    @Column(nullable = false)
    private LocalDate awardDate;

    @Column(length = 50)
    private String rank;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer displayOrder = 0;

    @Column(nullable = false)
    private Boolean deleted = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Award(Long awardSeq, String title, String organization, LocalDate awardDate,
                 String rank, String description, Integer displayOrder,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.awardSeq = awardSeq;
        this.title = title;
        this.organization = organization;
        this.awardDate = awardDate;
        this.rank = rank;
        this.description = description;
        this.displayOrder = displayOrder != null ? displayOrder : 0;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 수정 메서드
    public void update(String title, String organization, LocalDate awardDate,
                       String rank, String description, Integer displayOrder) {
        if (awardDate == null) {
            throw new IllegalArgumentException("수상일은 필수입니다.");
        }

        this.title = title;
        this.organization = organization;
        this.awardDate = awardDate;
        this.rank = rank;
        this.description = description;
        if (displayOrder != null) {
            this.displayOrder = displayOrder;
        }
    }

    // 순서 변경
    public void updateDisplayOrder(Integer displayOrder) {
        if (displayOrder != null && displayOrder >= 0) {
            this.displayOrder = displayOrder;
        }
    }

    // Soft Delete
    public void delete() {
        this.deleted = true;
    }

    // 복구
    public void restore() {
        this.deleted = false;
    }
}