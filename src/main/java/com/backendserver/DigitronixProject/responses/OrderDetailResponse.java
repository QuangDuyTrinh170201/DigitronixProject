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

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_image")
    private String productImage;

    private Long quantity;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        OrderDetailResponse orderDetailResponse = null;
        orderDetailResponse = OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .productName(orderDetail.getProduct().getProductName())
                .quantity(orderDetail.getQuantity())
                .productImage(orderDetail.getProduct().getImg())
                .build();
        orderDetailResponse.setCreatedAt(orderDetail.getCreatedAt());
        orderDetailResponse.setUpdatedAt(orderDetail.getUpdatedAt());
        return orderDetailResponse;
    }
}
