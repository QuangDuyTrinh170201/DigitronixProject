package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDetailDTO {
    @JsonProperty("details_name")
    @NotBlank(message = "Detail name is required")
    private String detailName;

    @NotBlank(message = "Intensity is required")
    private Long intensity;

    @JsonProperty("process_id")
    @NotBlank(message = "Process id is required!")
    private Long processId;

    @JsonProperty("is_final")
    private Boolean isFinal;

    @JsonProperty("in_material_id")
    @NotBlank(message = "You must input material id for process!")
    private Long inMaterialId;

    @JsonProperty("out_id")
    @NotBlank(message = "You must input the product after process!")
    private Long outId;
}
