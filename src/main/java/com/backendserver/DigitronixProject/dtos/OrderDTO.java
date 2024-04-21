package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @JsonProperty("created_date")
    @NotBlank(message = "Created date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @NotBlank(message = "Deadline is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;

    @JsonProperty("delivery_method")
    private String deliveryMethod;

    @JsonProperty("payment_method")
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @JsonProperty("total_price")
    @NotBlank(message = "Total price is required")
    private Double totalPrice;

    @JsonProperty("status")
    @NotBlank(message = "Status is required")
    private String status;

    @JsonProperty("user_id")
    @NotBlank(message = "User id is required")
    private Long userId;

    @JsonProperty("customer_id")
    @NotBlank(message = "Customer id is required")
    private Long customerId;

    @JsonProperty("order_detail_list")
    private List<OrderDetailDTO> orderDetailDTOList;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;
}
