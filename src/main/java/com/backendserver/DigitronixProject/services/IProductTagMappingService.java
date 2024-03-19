package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.models.ProductTagMapping;

public interface IProductTagMappingService {
    void removeTagOrProduct(Long productId, Long tagId);
}
