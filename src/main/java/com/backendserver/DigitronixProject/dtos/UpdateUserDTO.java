package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    @JsonProperty("username")
    @NotBlank(message = "User name is required")
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("is_active")
    private Boolean isActive;
}
