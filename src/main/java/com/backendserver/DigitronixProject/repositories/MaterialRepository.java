package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByName(String name);

    void deleteByName(String name);

    @Query("SELECT DISTINCT m FROM Material m LEFT JOIN FETCH m.tags t " +
            "WHERE (:categoryId IS NULL OR :categoryId = 0 OR m.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
    Page<Material> searchMaterial(@Param("categoryId") Long categoryId,
                                  @Param("keyword") String keyword, Pageable pageable);


}
