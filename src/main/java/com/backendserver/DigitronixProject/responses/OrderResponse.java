package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Material;
import com.backendserver.DigitronixProject.models.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse extends BaseResponse{

    private Long id;
    @JsonProperty("created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;

    @JsonProperty("delivery_method")
    private String deliveryMethod;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("status")
    private String status;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("customer_id")
    private Long customerId;

    private String customerAddress;

    private List<OrderDetailResponse> orderDetailResponses;

    public static OrderResponse fromOrder(Order order) {
        OrderResponse orderResponse = null;
        orderResponse = OrderResponse.builder()
                .id(order.getId())
                .createdDate(order.getCreatedDate())
                .deadline(order.getDeadline())
                .deliveryMethod(order.getDeliveryMethod())
                .paymentMethod(order.getPaymentMethod())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .userId(order.getUser().getId())
                .userName(order.getUser().getUsername())
                .orderDetailResponses(order.getOrderDetails() != null ?
                        order.getOrderDetails().stream().map(OrderDetailResponse::fromOrderDetail).toList() :
                        Collections.emptyList()) // Trả về mảng rỗng nếu processDetails là null
                .customerId(order.getCustomer().getId())
                .customerName(order.getCustomer().getName())
                .customerAddress(order.getCustomer().getAddress())
                .build();
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setUpdatedAt(order.getUpdatedAt());
        return orderResponse;
    }
}
