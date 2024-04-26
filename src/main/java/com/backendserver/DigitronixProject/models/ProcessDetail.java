package com.backendserver.DigitronixProject.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "process_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "details_name", nullable = false)
    private String detailName;

    @Column(name = "intensity", nullable = false)
    private Long intensity;

    @Column(name = "is_final")
    private Boolean isFinal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_material_id", nullable = false)
    private Material material;

    @Column(name = "out_id", nullable = false)
    private Long outId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    @JsonBackReference
    private Process process;
}
