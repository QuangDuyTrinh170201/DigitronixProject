package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.models.ProcessDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessDetailResponse extends BaseResponse{
    private Long id;

    @JsonProperty("detail_name")
    private String detailName;

    private Long intensity;

    @JsonProperty("process_id")
    private Long processId;

    public static ProcessDetailResponse fromProcessDetail(ProcessDetail processDetail){
        ProcessDetailResponse response = ProcessDetailResponse.builder()
                .id(processDetail.getId())
                .detailName(processDetail.getDetailName())
                .intensity(processDetail.getIntensity())
                .processId(processDetail.getProcess().getId())
                .build();
        response.setCreatedAt(processDetail.getCreatedAt());
        response.setUpdatedAt(processDetail.getUpdatedAt());

        return response;
    }
}
