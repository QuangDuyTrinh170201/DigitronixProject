package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialTagMappingDTO {
    @JsonProperty("material_id")
    @NotBlank(message = "Material id is required")
    private Long materialId;

    @JsonProperty("tag_id")
    @NotBlank(message = "Tag id is required")
    private Long tagId;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;
}
