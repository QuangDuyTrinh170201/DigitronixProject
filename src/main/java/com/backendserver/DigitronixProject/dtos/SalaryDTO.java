package com.backendserver.DigitronixProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO {
    @JsonProperty("user_id")
    @NotBlank(message = "User id is required")
    private Long userId;

    @JsonProperty("salary_per_date")
    @NotBlank(message = "Salary per date is required")
    private float salaryPerDate;

    @JsonProperty("working_date")
    @NotBlank(message = "Working date is required")
    private float workingDate;

    @JsonProperty("min_kpi")
    @NotBlank(message = "Min KPI is required")
    private int minKpi;

    @JsonProperty("rate_sa")
    private float rateSa;

    private double total;
}
