package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
//    @NotBlank(message = "Order id is required")
    private Long orderId;

    @JsonProperty("product_id")
    @NotBlank(message = "Product id is required")
    private Long productId;

    @NotBlank(message = "Quantity is required")
    private Long quantity;
}
