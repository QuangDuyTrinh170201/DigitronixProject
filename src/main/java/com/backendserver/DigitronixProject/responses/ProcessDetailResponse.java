package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.ProcessDetail;
import com.backendserver.DigitronixProject.services.Material.IMaterialService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class ProcessDetailResponse extends BaseResponse{

    private Long id;

    @JsonProperty("detail_name")
    private String detailName;

    private Long intensity;

    @JsonProperty("process_id")
    private Long processId;

    @JsonProperty("is_final")
    private Boolean isFinal;

    @JsonProperty("in_material_id")
    private Long inMaterialId;

    @JsonProperty("material_name")
    private String materialName;

    @JsonProperty("out_id")
    private Long outId;

    public static ProcessDetailResponse fromProcessDetail(ProcessDetail processDetail){
        ProcessDetailResponse response = ProcessDetailResponse.builder()
                .id(processDetail.getId())
                .detailName(processDetail.getDetailName())
                .intensity(processDetail.getIntensity())
                .processId(processDetail.getProcess().getId())
                .isFinal(processDetail.getIsFinal())
                .inMaterialId(processDetail.getMaterial().getId())
                .materialName(processDetail.getMaterial().getName())
                .outId(processDetail.getOutId())
                .build();
        response.setCreatedAt(processDetail.getCreatedAt());
        response.setUpdatedAt(processDetail.getUpdatedAt());
        return response;
    }
}
