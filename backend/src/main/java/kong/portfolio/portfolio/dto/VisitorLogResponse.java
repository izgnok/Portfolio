package kong.portfolio.portfolio.dto;

import kong.portfolio.portfolio.entity.VisitorLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 방문자 로그 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorLogResponse {

    private Long visitorLogSeq;
    private String ipAddress;
    private String pageUrl;
    private String device;
    private String userAgent;
    private Integer durationSeconds;
    private LocalDateTime visitedAt;

    public static VisitorLogResponse from(VisitorLog log) {
        return VisitorLogResponse.builder()
                .visitorLogSeq(log.getVisitorLogSeq())
                .ipAddress(log.getIpAddress())
                .pageUrl(log.getPageUrl())
                .device(log.getDevice())
                .userAgent(log.getUserAgent())
                .durationSeconds(log.getDurationSeconds())
                .visitedAt(log.getVisitedAt())
                .build();
    }
}