package com.backendserver.DigitronixProject.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String name;

    public static String DIRECTOR = "DIRECTOR";
    public static String PRODUCTION_MANAGER = "PRODUCTION_MANAGER";
    public static String INVENTORY_MANAGER = "INVENTORY_MANAGER";
    public static String SALE = "SALE";
    public static String WORKER = "WORKER";
    public static String DRIVER = "DRIVER";
}
