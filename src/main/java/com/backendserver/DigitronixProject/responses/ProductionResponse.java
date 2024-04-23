package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Production;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductionResponse extends BaseResponse{
    private Long id;
    @JsonProperty("time_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStart;

    @JsonProperty("time_end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeEnd;

    private Long quantity;

    private String status;

    @JsonProperty("total_cost")
    private Double totalCost;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("process_id")
    private Long processId;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("production_detail")
    private List<ProductionDetailResponse> productionDetailResponseList;

    public static ProductionResponse fromProduction(Production production){
        ProductionResponse response = ProductionResponse.builder()
                .id(production.getId())
                .productName(production.getProcess().getProduct().getProductName())
                .timeStart(production.getTimeStart())
                .timeEnd(production.getTimeEnd())
                .quantity(production.getQuantity())
                .status(production.getStatus())
                .totalCost(production.getTotalCost())
                .userId(production.getUser().getId())
                .processId(production.getProcess().getId())
                .productionDetailResponseList(production.getProductionDetails() != null ?
                        production.getProductionDetails().stream().map(ProductionDetailResponse::fromProductionDetail).toList() :
                        Collections.emptyList())
                .orderId(production.getOrder().getId())
                .build();

        response.setCreatedAt(production.getCreatedAt());
        response.setUpdatedAt(production.getUpdatedAt());

        return response;
    }
}
