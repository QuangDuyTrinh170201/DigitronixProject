package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDTO {
    @JsonProperty("time_start")
    @NotBlank(message = "Time start is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStart;

    @JsonProperty("time_end")
    @NotBlank(message = "Time end is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeEnd;

    @NotBlank(message = "Quantity is required")
    private Long quantity;

    @NotBlank(message = "Status is required")
    private String status;

    @JsonProperty("total_cost")
    private Double totalCost;

    @JsonProperty("user_id")
    @NotBlank(message = "User id is required")
    private Long userId;

    @JsonProperty("process_id")
    @NotBlank(message = "process id is required")
    private Long processId;

    @JsonProperty("order_id")
    @NotBlank(message = "Order id is required")
    private Long orderId;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;
}
