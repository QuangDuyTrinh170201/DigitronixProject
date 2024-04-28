package com.backendserver.DigitronixProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "material_data_accesses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialDataAccess extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Material id is required")
    @Column(name = "material_id")
    private int materialId;

    @NotBlank(message = "Material name is required")
    @Column(name = "material_name")
    private String materialName;

    @NotNull(message = "Material quantity is required")
    @Column(name = "material_quantity")
    private int materialQuantity;

    @NotNull(message = "Status is required")
    @Column(name = "access_status")
    private boolean status;
}
