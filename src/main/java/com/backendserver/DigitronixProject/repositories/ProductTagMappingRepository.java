package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.ProductTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTagMappingRepository extends JpaRepository<ProductTagMapping, Long> {
    ProductTagMapping findByProduct_IdAndTag_Id (Long productId, Long tagId);
}
