package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDataAccessDTO {
    @NotBlank(message = "Material id is required")
    @JsonProperty("material_id")
    private int materialId;

    @NotBlank(message = "Material name is required")
    @JsonProperty("material_name")
    private String materialName;

    @NotBlank(message = "Material quantity is required")
    @JsonProperty("material_quantity")
    private int materialQuantity;

    @NotBlank(message = "Status is required")
    @JsonProperty("access_status")
    private boolean status;
}
