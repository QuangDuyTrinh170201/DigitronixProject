package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Material;
import com.backendserver.DigitronixProject.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByMaterialName(String name);

    void deleteByProductName(String name);

    @Query("SELECT DISTINCT m FROM Material m LEFT JOIN FETCH m.tags t " +
            "WHERE (:categoryId IS NULL OR :categoryId = 0 OR m.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR m.name LIKE %:keyword%)")
    Page<Material> searchMaterial(@Param("categoryId") Long categoryId,
                                 @Param("keyword") String keyword, Pageable pageable);

}
