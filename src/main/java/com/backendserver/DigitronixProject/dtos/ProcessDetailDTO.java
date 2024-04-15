package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDetailDTO {
    @JsonProperty("details_name")
    @NotBlank(message = "Detail name is required")
    private String detailName;

    @NotBlank(message = "Intensity is required")
    private Long intensity;

    @JsonProperty("process_id")
    @NotBlank(message = "Process id is required!")
    private Long processId;
}
