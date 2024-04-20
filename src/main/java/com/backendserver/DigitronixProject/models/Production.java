package com.backendserver.DigitronixProject.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "productions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Production extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_start", nullable = false)
    private LocalDateTime timeStart;

    @Column(name = "time_end", nullable = false)
    private LocalDateTime timeEnd;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_cost")
    private Double totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private Process process;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
