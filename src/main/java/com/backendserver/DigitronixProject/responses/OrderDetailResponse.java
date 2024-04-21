package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Delivery;
import com.backendserver.DigitronixProject.models.OrderDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse extends BaseResponse{
    private Long id;
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    private Long quantity;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        OrderDetailResponse orderDetailResponse = null;
        orderDetailResponse = OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .quantity(orderDetail.getQuantity())
                .build();
        orderDetailResponse.setCreatedAt(orderDetail.getCreatedAt());
        orderDetailResponse.setUpdatedAt(orderDetail.getUpdatedAt());
        return orderDetailResponse;
    }
}
