package com.backendserver.DigitronixProject.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "processes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Process extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "process_name", nullable = false)
    private String processName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "process", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProcessDetail> processDetails;
}
