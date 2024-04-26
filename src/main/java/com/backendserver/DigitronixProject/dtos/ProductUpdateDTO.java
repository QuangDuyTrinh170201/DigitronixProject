package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {

    @NotBlank(message = "Product name is required")
    @JsonProperty("product_name")
    private String productName;

    private Double price;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("img_url")
    private String img;

    @NotNull(message = "Quantity is required")
    private int quantity;

    private int missing;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;
}

