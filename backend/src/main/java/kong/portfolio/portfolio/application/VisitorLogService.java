package kong.portfolio.portfolio.application;

import kong.portfolio.portfolio.dto.VisitorLogRequest;
import kong.portfolio.portfolio.dto.VisitorStatsResponse;
import kong.portfolio.portfolio.entity.VisitorLog;
import kong.portfolio.portfolio.infrastructure.VisitorLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 방문자 로그 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorLogService {

    private final VisitorLogRepository visitorLogRepository;

    /**
     * 방문 로그 기록
     */
    @Transactional
    public void logVisit(VisitorLogRequest request) {
        VisitorLog visitorLog = VisitorLog.builder()
                .ipAddress(request.getIpAddress())
                .pageUrl(request.getPageUrl())
                .device(request.getDevice())
                .userAgent(request.getUserAgent())
                .durationSeconds(request.getDurationSeconds())
                .build();

        visitorLogRepository.save(visitorLog);
        log.debug("방문 로그 기록: {} - {}", request.getIpAddress(), request.getPageUrl());
    }

    /**
     * 방문 통계 조회 (기간별)
     */
    public VisitorStatsResponse getVisitorStats(LocalDateTime startDate, LocalDateTime endDate) {
        // 전체 방문자 수
        long totalVisitors = visitorLogRepository.countUniqueVisitors();

        // 오늘 방문자 수
        long todayVisitors = visitorLogRepository.countTodayVisitors();

        // 기간 내 방문자 수
        long periodVisitors = visitorLogRepository.countByVisitedAtBetween(startDate, endDate);

        // 페이지별 방문 횟수
        List<Object[]> pageStatsData = visitorLogRepository.countByPageUrl(startDate, endDate);
        Map<String, Long> pageStats = new HashMap<>();
        for (Object[] stat : pageStatsData) {
            pageStats.put((String) stat[0], (Long) stat[1]);
        }

        // 디바이스별 방문 횟수
        List<Object[]> deviceStatsData = visitorLogRepository.countByDevice(startDate, endDate);
        Map<String, Long> deviceStats = new HashMap<>();
        for (Object[] stat : deviceStatsData) {
            deviceStats.put((String) stat[0], (Long) stat[1]);
        }

        // 일별 방문자 수
        List<Object[]> dailyStatsData = visitorLogRepository.countDailyVisitors(startDate, endDate);
        Map<String, Long> dailyStats = new HashMap<>();
        for (Object[] stat : dailyStatsData) {
            dailyStats.put(stat[0].toString(), (Long) stat[1]);
        }

        // 평균 체류 시간
        Double avgDuration = visitorLogRepository.getAverageDuration(startDate, endDate);

        return VisitorStatsResponse.builder()
                .totalVisitors(totalVisitors)
                .todayVisitors(todayVisitors)
                .periodVisitors(periodVisitors)
                .pageStats(pageStats)
                .deviceStats(deviceStats)
                .dailyStats(dailyStats)
                .averageDuration(avgDuration != null ? avgDuration : 0.0)
                .build();
    }


    /**
     * 오늘 방문자 수
     */
    public long getTodayVisitors() {
        return visitorLogRepository.countTodayVisitors();
    }

    /**
     * 전체 방문자 수 (IP 기준)
     */
    public long getTotalUniqueVisitors() {
        return visitorLogRepository.countUniqueVisitors();
    }

    /**
     * 특정 기간 방문 로그 조회
     */
    public List<VisitorLog> getVisitorLogs(LocalDateTime startDate, LocalDateTime endDate) {
        return visitorLogRepository.findAllByVisitedAtBetweenOrderByVisitedAtDesc(startDate, endDate);
    }

    /**
     * 체류 시간 업데이트
     */
    @Transactional
    public void updateDuration(Long visitorLogSeq, Integer durationSeconds) {
        VisitorLog visitorLog = visitorLogRepository.findById(visitorLogSeq)
                .orElseThrow(() -> new IllegalArgumentException("방문 로그를 찾을 수 없습니다."));

        visitorLog.updateDuration(durationSeconds);
        log.debug("체류 시간 업데이트: {} - {}초", visitorLogSeq, durationSeconds);
    }
}
