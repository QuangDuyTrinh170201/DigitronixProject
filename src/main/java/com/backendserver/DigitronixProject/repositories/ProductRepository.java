package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Tìm sản phẩm theo tên
    Optional<Product> findByProductName(String productName);

    // Xóa sản phẩm theo tên
    void deleteByProductName(String productName);
//    void addTagToProduct(Long productId, Long tagId);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tags t " +
            "WHERE (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.productName LIKE %:keyword%)")
    Page<Product> searchProducts(@Param("categoryId") Long categoryId,
                                 @Param("keyword") String keyword, Pageable pageable);

    Optional<Product> findById(@Param("productId") Long productId);
}
