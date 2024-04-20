package com.backendserver.DigitronixProject.services.Product;

import com.backendserver.DigitronixProject.dtos.ProductDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Product;
import com.backendserver.DigitronixProject.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.security.InvalidParameterException;
import java.util.List;

public interface IProductService {
    Product createProductInfo(ProductDTO productDTO) throws DataNotFoundException, InvalidParameterException;

    Product updateProductImage(Long productId, String fileName) throws DataNotFoundException;
    Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);
    Product addTagToProduct(Long productId, Long tagId);
    String deleteProduct(Long productId) throws DataNotFoundException;
    ProductResponse updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;
    Product getProductById(long productId) throws Exception;
    List<ProductResponse> getAllProductNoPaging() throws DataNotFoundException;
}
