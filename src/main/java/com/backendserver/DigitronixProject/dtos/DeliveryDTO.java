package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    @JsonProperty("order_id")
    @NotBlank(message = "Order id is required")
    private Long orderId;

    @JsonProperty("delivery_date")
    @NotBlank(message = "Delivery date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveryDate;

    @NotBlank(message = "Status is required")
    private Boolean status;
}
