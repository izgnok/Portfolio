package kong.portfolio.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 방문자 통계 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorStatsResponse {

    private Long totalVisitors;
    private Long todayVisitors;
    private Long periodVisitors;
    private Double averageDuration;

    // 페이지별 통계
    private Map<String, Long> pageStats;

    // 디바이스별 통계
    private Map<String, Long> deviceStats;

    // 일별 통계
    private Map<String, Long> dailyStats;
}