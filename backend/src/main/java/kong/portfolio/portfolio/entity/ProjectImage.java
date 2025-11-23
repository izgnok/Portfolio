package kong.portfolio.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProjectImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] imageData;
    
    @Column(nullable = false, length = 100)
    private String imageType;
    
    @Column(nullable = false)
    private Integer displayOrder;

}
