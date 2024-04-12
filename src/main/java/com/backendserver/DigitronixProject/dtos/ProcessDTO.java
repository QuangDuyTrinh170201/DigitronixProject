package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDTO {
    @JsonProperty("process_name")
    @NotBlank(message = "Process name is required")
    private String processName;

    @JsonProperty("product_id")
    @NotBlank(message = "Product id is required")
    private Long productId;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;
}
