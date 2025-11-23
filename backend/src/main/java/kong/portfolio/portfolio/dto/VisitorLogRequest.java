package kong.portfolio.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 방문자 로그 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorLogRequest {

    private String ipAddress;
    private String pageUrl;
    private String device;
    private String userAgent;
    private Integer durationSeconds;
}