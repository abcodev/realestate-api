package realestate.server.application.rag.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import realestate.server.application.common.response.ApiResponse;
import realestate.server.application.rag.application.RagDocumentBuildResult;
import realestate.server.application.rag.application.RagDocumentBuildService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rag/documents")
@Tag(name = "RAG Documents", description = "RAG 문서 생성 API")
public class RagDocumentController {

    private final RagDocumentBuildService buildService;

    @PostMapping("/deals")
    @Operation(summary = "실거래가 RAG 문서 생성", description = "real_estate_deals 데이터를 rag_document 문서로 변환해 저장합니다. limit이 0 이하이면 전체를 처리합니다.")
    public ApiResponse<RagDocumentBuildResult> buildDealDocuments(
            @RequestParam(defaultValue = "1000") int limit) {
        return ApiResponse.success(buildService.buildDealDocuments(limit));
    }
}
