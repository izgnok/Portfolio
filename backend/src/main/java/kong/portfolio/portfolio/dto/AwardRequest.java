package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 수상 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwardRequest {

    @NotBlank(message = "수상명은 필수입니다.")
    private String title;

    @NotBlank(message = "주최기관은 필수입니다.")
    private String organization;

    @NotNull(message = "수상일은 필수입니다.")
    private LocalDate awardDate;

    private String rank;
    private String description;
    private Integer displayOrder;
}