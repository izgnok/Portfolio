package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String keyword;

    @Column(nullable = false)
    private Integer displayOrder;  // 표시 순서

    public void updateDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public void updateKeyword(String keyword) {
        this.keyword = keyword;
    }
}
