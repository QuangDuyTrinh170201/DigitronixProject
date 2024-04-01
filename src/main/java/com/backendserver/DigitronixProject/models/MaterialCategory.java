package com.backendserver.DigitronixProject.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "material_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialCategory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material_category_name", nullable = false)
    private String name;
}
