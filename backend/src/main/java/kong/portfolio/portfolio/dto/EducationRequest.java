package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequest {
    
    @NotBlank(message = "학교명/기관명을 입력해주세요")
    private String name;
    
    @NotNull(message = "시작일을 입력해주세요")
    private LocalDate startDate;
    
    @NotNull(message = "종료일을 입력해주세요")
    private LocalDate endDate;
    
    @NotBlank(message = "진행상태를 입력해주세요")
    private String status;  // 졸업예정, 졸업, 이수중, 수료
    
    private String gpa;  // null 가능
}
