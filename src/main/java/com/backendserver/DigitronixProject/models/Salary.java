package com.backendserver.DigitronixProject.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salaries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Salary extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "salary_per_date", nullable = false)
    private float salaryPerDate;

    @Column(name = "working_date", nullable = false)
    private float workingDate;

    @Column(name = "min_kpi", nullable = false)
    private int minKpi;

    @Column(name = "rate_sa", nullable = false)
    private float rateSa;

    @Column(name = "total", nullable = false)
    private double total;
}
