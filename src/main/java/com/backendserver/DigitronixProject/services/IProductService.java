package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.ProductDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Product;
import com.backendserver.DigitronixProject.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.security.InvalidParameterException;
import java.util.List;

public interface IProductService {
    public Product createProductInfo(ProductDTO productDTO) throws DataNotFoundException, InvalidParameterException;

    public Product updateProductImage(Long productId, String fileName) throws DataNotFoundException;
    public Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);
    public Product addTagToProduct(Long productId, Long tagId);
    public String deleteProduct(Long productId) throws DataNotFoundException;
    public Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;
//  public Product updateProductImageAfter(Long productId, String fileName) throws DataNotFoundException;
//  public Page<ProductResponse> getProductsWithImage(String keyword, Long categoryId, PageRequest pageRequest);
    public Product getProductById(long productId) throws Exception;
}
