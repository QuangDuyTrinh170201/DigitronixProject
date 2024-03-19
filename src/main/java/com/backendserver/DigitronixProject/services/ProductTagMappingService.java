package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.models.ProductTagMapping;
import com.backendserver.DigitronixProject.repositories.ProductTagMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTagMappingService implements IProductTagMappingService{
    private final ProductTagMappingRepository productTagMappingRepository;
    @Override
    public void removeTagOrProduct(Long productId, Long tagId) {
        ProductTagMapping mapping = productTagMappingRepository.findByProduct_IdAndTag_Id(productId, tagId);
        Long mappingId = mapping.getId();
        productTagMappingRepository.deleteById(mappingId);
    }
}
