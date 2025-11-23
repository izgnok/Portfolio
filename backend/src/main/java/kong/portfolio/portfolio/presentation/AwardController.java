package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.AwardService;
import kong.portfolio.portfolio.dto.AwardRequest;
import kong.portfolio.portfolio.dto.AwardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/awards")
@RequiredArgsConstructor
public class AwardController {

    private final AwardService awardService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllAwards() {
        List<AwardResponse> response = awardService.getAllAwards();
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @GetMapping("/{awardSeq}")
    public ResponseEntity<ResponseDto> getAward(@PathVariable Long awardSeq) {
        AwardResponse response = awardService.getAward(awardSeq);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createAward(@Valid @RequestBody AwardRequest request) {
        AwardResponse response = awardService.createAward(request);
        return ResponseDto.response(StatusCode.CREATED, response);
    }

    @PutMapping("/{awardSeq}")
    public ResponseEntity<ResponseDto> updateAward(
            @PathVariable Long awardSeq,
            @Valid @RequestBody AwardRequest request) {
        AwardResponse response = awardService.updateAward(awardSeq, request);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @DeleteMapping("/{awardSeq}")
    public ResponseEntity<ResponseDto> deleteAward(@PathVariable Long awardSeq) {
        awardService.deleteAward(awardSeq);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/{awardSeq}/order")
    public ResponseEntity<ResponseDto> updateAwardOrder(
            @PathVariable Long awardSeq,
            @RequestParam Integer newOrder) {
        awardService.updateAwardOrder(awardSeq, newOrder);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/order")
    public ResponseEntity<ResponseDto> updateAwardsOrder(@RequestBody List<Long> awardSeqs) {
        awardService.updateAwardsOrder(awardSeqs);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }
}
