package realestate.server.application.realestate.interfaces.controller;

import realestate.server.application.common.response.ApiResponse;
import realestate.server.application.common.web.auth.CurrentUser;
import realestate.server.application.realestate.application.service.RealEstateSummaryCommand;
import realestate.server.application.realestate.application.service.RealEstateSummaryService;
import realestate.server.application.realestate.interfaces.dto.RealEstateSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realestate.server.application.realestate.application.service.RealEstateNewsService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/realestate")
@Tag(name = "부동산", description = "부동산 조회 관련 API")
public class RealEstateController {

    private final RealEstateSummaryService summaryService;

    private final RealEstateNewsService realEstateNewsService;

    @GetMapping("/summary")
    @Operation(summary = "비회원 부동산 정보 조회")
    public ResponseEntity<ApiResponse<RealEstateSummaryResponse>> findSummary(
            @RequestParam(required = false) String bgdCode) {
        RealEstateSummaryCommand command = RealEstateSummaryCommand.of(null, bgdCode);
        return ResponseEntity.ok(ApiResponse.of(summaryService.findSummary(command)));
    }

    @GetMapping("/summary/me")
    public ResponseEntity<ApiResponse<RealEstateSummaryResponse>> findMySummary(
            @CurrentUser Long userId) {
        RealEstateSummaryCommand command = RealEstateSummaryCommand.of(userId, null);
        return ResponseEntity.ok(ApiResponse.of(summaryService.findSummary(command)));
    }

    @PostMapping("/news-batch")
    @Operation(summary = "부동산 뉴스 AI 요약 수동 트리거", description = "최근 24시간 한국경제 부동산 기사를 긁어 AI 요약을 수행하고 저장합니다.")
    public ResponseEntity<ApiResponse<Void>> triggerNewsBatch() {
        log.info("부동산 뉴스 AI 요약 수동 실행 요청");
        realEstateNewsService.processDailyRealEstateNews();
        return ResponseEntity.ok(ApiResponse.empty());
    }

}
