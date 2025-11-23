package kong.portfolio.portfolio.infrastructure;

import kong.portfolio.portfolio.entity.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 방문자 로그 Repository
 */
@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long> {

    /**
     * 특정 기간 방문자 수 조회
     */
    long countByVisitedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 기간 방문 로그 조회
     */
    List<VisitorLog> findAllByVisitedAtBetweenOrderByVisitedAtDesc(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 페이지별 방문 횟수 통계
     */
    @Query("SELECT v.pageUrl, COUNT(v) FROM VisitorLog v " +
            "WHERE v.visitedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY v.pageUrl " +
            "ORDER BY COUNT(v) DESC")
    List<Object[]> countByPageUrl(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);

    /**
     * 디바이스별 방문 횟수 통계
     */
    @Query("SELECT v.device, COUNT(v) FROM VisitorLog v " +
            "WHERE v.visitedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY v.device " +
            "ORDER BY COUNT(v) DESC")
    List<Object[]> countByDevice(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    /**
     * 일별 방문자 수 통계
     */
    @Query("SELECT FUNCTION('DATE', v.visitedAt), COUNT(DISTINCT v.ipAddress) FROM VisitorLog v " +
            "WHERE v.visitedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('DATE', v.visitedAt) " +
            "ORDER BY FUNCTION('DATE', v.visitedAt) ASC")
    List<Object[]> countDailyVisitors(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

    /**
     * 평균 체류 시간 조회
     */
    @Query("SELECT AVG(v.durationSeconds) FROM VisitorLog v " +
            "WHERE v.visitedAt BETWEEN :startDate AND :endDate " +
            "AND v.durationSeconds IS NOT NULL")
    Double getAverageDuration(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);

    /**
     * 전체 방문자 수 (IP 기준)
     */
    @Query("SELECT COUNT(DISTINCT v.ipAddress) FROM VisitorLog v")
    long countUniqueVisitors();

    /**
     * 오늘 방문자 수
     */
    @Query("SELECT COUNT(DISTINCT v.ipAddress) FROM VisitorLog v " +
            "WHERE FUNCTION('DATE', v.visitedAt) = FUNCTION('DATE', CURRENT_TIMESTAMP)")
    long countTodayVisitors();
}