package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Customer;
import com.backendserver.DigitronixProject.models.Delivery;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryResponse extends BaseResponse{
    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    private OrderResponse orderResponse;

    @JsonProperty("delivery_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveryDate;

    private Boolean status;

    public static DeliveryResponse fromDelivery(Delivery delivery) {
        DeliveryResponse deliveryResponse = null;
        deliveryResponse = DeliveryResponse.builder()
                .id(delivery.getId())
                .deliveryDate(delivery.getDeliveryDate())
                .status(delivery.getStatus())
                .orderResponse(OrderResponse.fromOrder(delivery.getOrder()))
                .build();
        deliveryResponse.setCreatedAt(delivery.getCreatedAt());
        deliveryResponse.setUpdatedAt(delivery.getUpdatedAt());
        return deliveryResponse;
    }
}
