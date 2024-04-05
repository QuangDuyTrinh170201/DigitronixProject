package com.backendserver.DigitronixProject.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "materials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Material extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material_name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private MaterialCategory category;

    @Column(name = "image",nullable = false)
    private String image;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ManyToMany
    @JoinTable(
            name = "material_tags_mapping",
            joinColumns = @JoinColumn(name = "material_name"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}
