package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.MaterialDataAccess;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialDataAccessResponse extends BaseResponse{
    private Long id;

    @JsonProperty("material_id")
    private int materialId;

    @JsonProperty("material_name")
    private String materialName;

    @JsonProperty("material_quantity")
    private int materialQuantity;

    @JsonProperty("access_status")
    private boolean status;

    public static MaterialDataAccessResponse fromMaterialDataAccess(MaterialDataAccess materialDataAccess){
        MaterialDataAccessResponse response = MaterialDataAccessResponse.builder()
                .id(materialDataAccess.getId())
                .materialId(materialDataAccess.getMaterialId())
                .materialName(materialDataAccess.getMaterialName())
                .materialQuantity(materialDataAccess.getMaterialQuantity())
                .status(materialDataAccess.isStatus())
                .build();
        response.setCreatedAt(materialDataAccess.getCreatedAt());
        response.setUpdatedAt(materialDataAccess.getUpdatedAt());
        return response;
    }
}
