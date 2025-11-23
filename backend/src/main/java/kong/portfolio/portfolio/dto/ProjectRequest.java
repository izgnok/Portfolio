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
public class ProjectRequest {
    
    @NotBlank(message = "프로젝트명을 입력해주세요")
    private String name;
    
    @NotNull(message = "팀인원을 입력해주세요")
    private Integer teamSize;
    
    @NotNull(message = "시작일을 입력해주세요")
    private LocalDate startDate;
    
    @NotNull(message = "종료일을 입력해주세요")
    private LocalDate endDate;
    
    @NotBlank(message = "진행 상태를 입력해주세요")
    private String status;  // 진행중, 완료
    
    @NotNull(message = "수상 여부를 입력해주세요")
    private Boolean hasAward;
    
    private String awardName;  // null 가능
    private String awardOrganization;  // null 가능
    
    // JSON 문자열로 전달 (프론트에서 JSON.stringify()해서 보냄)
    private String summaries;
    private String coreValues;
    private String mainFeatures;
    private String roles;
    
    // 기술스택
    private String techDatabase;
    private String techBackend;
    private String techFrontend;
    private String techIot;
    private String techCicd;
    private String techExternalApi;
    
    // 성과 및 문제해결
    private String problemSolutions;  // [{"problem":"문제","solution":"해결"}]
    private String achievements;
    
    // 아쉬운점/개선방안
    private String regrets;
    private String improvements;
}
