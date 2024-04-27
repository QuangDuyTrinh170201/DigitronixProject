package com.backendserver.DigitronixProject.responses;
import com.backendserver.DigitronixProject.models.ProductionDetail;
import com.backendserver.DigitronixProject.repositories.ProductionDetailRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductionDetailResponse {
    private Long id;
    private String name;

    @JsonProperty("time_start")
    private LocalDateTime timeStart;

    @JsonProperty("time_end")
    private LocalDateTime timeEnd;
    private String status;

    private Float cost;

    @JsonProperty("process_detail_id")
    private Long processDetailId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("in_material_quantity")
    private int inMaterialQuantity;

    @JsonProperty("out_quantity")
    private int outQuantity;

    @JsonProperty("production_id")
    private Long productionId;

    public static ProductionDetailResponse fromProductionDetail(ProductionDetail productionDetail) {

        return ProductionDetailResponse.builder()
                .id(productionDetail.getId())
                .name(productionDetail.getName())
                .timeStart(productionDetail.getTimeStart())
                .timeEnd(productionDetail.getTimeEnd())
                .status(productionDetail.getStatus())
                .cost(productionDetail.getCost())
                .inMaterialQuantity(productionDetail.getInMaterialQuantity())
                .outQuantity(productionDetail.getOutQuantity())
                .processDetailId(productionDetail.getProcessDetail().getId())
                .userId(productionDetail.getUser().getId())
                .productionId(productionDetail.getProduction().getId())
                .build();
    }
}
