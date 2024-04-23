package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDetailDTO {
    @NotBlank(message = "Production detail name is required")
    private String name;

    @JsonProperty("time_start")
    private LocalDateTime timeStart;

    @JsonProperty("time_end")
    private LocalDateTime timeEnd;

    private String status;

    private Float cost;

    @JsonProperty("process_detail_id")
    @NotBlank(message = "Process detail is required")
    private Long processDetailId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("in_material_quantity")
    private int inMaterialQuantity;

    @JsonProperty("out_quantity")
    private int outQuantity;

    @JsonProperty("production_id")
    @NotBlank(message = "Production is required")
    private Long productionId;
}
