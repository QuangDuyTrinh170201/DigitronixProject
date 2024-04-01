package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.MaterialCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialCategoryRepository extends JpaRepository<MaterialCategory, Long> {
    boolean existsByName(String name);

    Optional<MaterialCategory> findByName(String name);
}
