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
public class MaterialDTO {
    @JsonProperty("material_name")
    @NotBlank(message = "Material name is required")
    private String name;

    @NotBlank(message = "Price is required")
    private Double price;

    @NotBlank(message = "Quantity is required")
    private Long quantity;

    @JsonProperty("category_id")
    @NotBlank(message = "Category is required")
    private Long categoryId;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;
}
