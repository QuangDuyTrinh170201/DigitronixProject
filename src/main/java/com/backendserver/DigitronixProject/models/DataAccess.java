package com.backendserver.DigitronixProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "data_accesses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataAccess extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Product id is required")
    @Column(name = "product_id")
    private int productId;

    @NotBlank(message = "Product name is required")
    @Column(name = "product_name")
    private String productName;

    @NotNull(message = "Product quantity is required")
    @Column(name = "product_quantity")
    private int productQuantity;

    @NotNull(message = "Status is required")
    @Column(name = "access_status")
    private boolean status;
}
