package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Salary;
import com.backendserver.DigitronixProject.models.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryResponse extends BaseResponse{
    private Long id;
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("salary_per_date")
    private float salaryPerDate;

    @JsonProperty("working_date")
    private float workingDate;

    @JsonProperty("min_kpi")
    private int minKpi;

    @JsonProperty("rate_sa")
    private float rateSa;

    @JsonProperty("user_name")
    private String userName;

    private double total;

    public static SalaryResponse fromSalary(Salary salary) {
        SalaryResponse salaryResponse = SalaryResponse.builder()
                .id(salary.getId())
                .userId(salary.getUser().getId())
                .userName(salary.getUser().getUsername())
                .salaryPerDate(salary.getSalaryPerDate())
                .workingDate(salary.getWorkingDate())
                .minKpi(salary.getMinKpi())
                .rateSa(salary.getRateSa())
                .total(salary.getTotal())
                .build();
        salaryResponse.setCreatedAt(salary.getCreatedAt());
        salaryResponse.setUpdatedAt(salary.getUpdatedAt());
        return salaryResponse;
    }
}
