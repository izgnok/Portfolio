package kong.portfolio.portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    
    @NotBlank(message = "영문명을 입력해주세요")
    private String nameEn;
    
    @NotNull(message = "생년월일을 입력해주세요")
    private LocalDate birthDate;
    
    private String phone;
    
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    
    private String github;
}
