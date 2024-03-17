package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataAccessDTO {
    @NotBlank(message = "Product id is required")
    @JsonProperty("product_id")
    private int productId;

    @NotBlank(message = "Product name is required")
    @JsonProperty("product_name")
    private String productName;

    @NotBlank(message = "Product quantity is required")
    @JsonProperty("product_quantity")
    private int productQuantity;

    @NotBlank(message = "Status is required")
    @JsonProperty("access_status")
    private boolean status;
}
