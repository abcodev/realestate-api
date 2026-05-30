package realestate.server.application.realestate.interfaces.controller;

import realestate.server.application.common.response.ApiResponse;
import realestate.server.application.common.web.auth.CurrentUser;
import realestate.server.application.realestate.application.service.RealEstateInterestService;
import realestate.server.application.realestate.domain.RegionCode;
import realestate.server.application.realestate.interfaces.dto.RealEstateRegionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/realestate")
@Tag(name = "부동산", description = "부동산 관심사 API")
public class RealEstateInterestController {

    private final RealEstateInterestService interestService;

    @GetMapping("/region")
    @Operation(summary = "지역 이름으로 2,3depth 부동산 지역 검색")
    public ApiResponse<List<RealEstateRegionResponse>> searchRegions(
            @RequestParam String regionName) {
        return ApiResponse.of(interestService.searchRegionsByName(regionName));
    }

    @PostMapping("/interest")
    @Operation(summary = "부동산 관심지역 저장")
    public ResponseEntity<ApiResponse<Void>> saveInterest(
            @CurrentUser Long userId,
            @RequestBody RegionCode regionCode) {
        interestService.saveInterest(userId, regionCode);
        return ResponseEntity.status(201).body(ApiResponse.success());
    }

}
