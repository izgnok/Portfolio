package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.VisitorLogService;
import kong.portfolio.portfolio.dto.VisitorLogRequest;
import kong.portfolio.portfolio.dto.VisitorStatsResponse;
import kong.portfolio.portfolio.entity.VisitorLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/visitor-logs")
@RequiredArgsConstructor
public class VisitorLogController {

    private final VisitorLogService visitorLogService;

    @PostMapping
    public ResponseEntity<ResponseDto> logVisit(@Valid @RequestBody VisitorLogRequest request) {
        visitorLogService.logVisit(request);
        return ResponseDto.response(StatusCode.CREATED, null);
    }

    @GetMapping("/stats")
    public ResponseEntity<ResponseDto> getVisitorStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        VisitorStatsResponse response = visitorLogService.getVisitorStats(startDate, endDate);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @GetMapping("/today")
    public ResponseEntity<ResponseDto> getTodayVisitors() {
        long count = visitorLogService.getTodayVisitors();
        return ResponseDto.response(StatusCode.SUCCESS, count);
    }

    @GetMapping("/total")
    public ResponseEntity<ResponseDto> getTotalUniqueVisitors() {
        long count = visitorLogService.getTotalUniqueVisitors();
        return ResponseDto.response(StatusCode.SUCCESS, count);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getVisitorLogs(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<VisitorLog> logs = visitorLogService.getVisitorLogs(startDate, endDate);
        return ResponseDto.response(StatusCode.SUCCESS, logs);
    }

    @PatchMapping("/{visitorLogSeq}/duration")
    public ResponseEntity<ResponseDto> updateDuration(
            @PathVariable Long visitorLogSeq,
            @RequestParam Integer durationSeconds) {
        visitorLogService.updateDuration(visitorLogSeq, durationSeconds);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }
}
