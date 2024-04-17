package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.models.ProcessDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessResponse extends BaseResponse{
    private Long id;

    @JsonProperty("process_name")
    private String processName;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("process_details")
    private List<ProcessDetailResponse> processDetails;

    public static ProcessResponse fromProcess(Process process){
        ProcessResponse response = ProcessResponse.builder()
                .id(process.getId())
                .processName(process.getProcessName())
                .productId(process.getProduct().getId())
                .processDetails(process.getProcessDetails().stream().map(ProcessDetailResponse::fromProcessDetail).toList())
                .build();
        response.setCreatedAt(process.getCreatedAt());
        response.setUpdatedAt(process.getUpdatedAt());

        return response;
    }
}
