package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialCategoryDTO {
    @NotEmpty(message = "Categories name cannot be empty")
    @JsonProperty("material_category_name")
    private String name;
}
