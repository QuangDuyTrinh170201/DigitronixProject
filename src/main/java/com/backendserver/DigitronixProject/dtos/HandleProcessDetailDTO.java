package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HandleProcessDetailDTO {
    private Long intensity;

    @JsonProperty("is_final")
    private Boolean isFinal;
}
