package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.ProductDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Category;
import com.backendserver.DigitronixProject.models.Product;
import com.backendserver.DigitronixProject.models.Tag;
import com.backendserver.DigitronixProject.repositories.CategoryRepository;
import com.backendserver.DigitronixProject.repositories.ProductRepository;
import com.backendserver.DigitronixProject.repositories.TagRepository;
import com.backendserver.DigitronixProject.responses.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    @Override
    @Transactional
    public Product createProductInfo(ProductDTO productDTO) throws DataNotFoundException, InvalidParameterException {
        // Tìm danh mục sản phẩm tương ứng
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Không thể tìm thấy danh mục sản phẩm với id: " + productDTO.getCategoryId()));

        // Kiểm tra xem có sản phẩm nào trùng tên không
        Optional<Product> existingProduct = productRepository.findByProductName(productDTO.getProductName());
        if(existingProduct.isPresent()) {
            throw new RuntimeException("Một sản phẩm cùng tên đã tồn tại.");
        }

        // Tạo sản phẩm mới
        Product newProduct = new Product();
        newProduct.setProductName(productDTO.getProductName());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setCategory(existingCategory);

        // Lưu sản phẩm mới vào cơ sở dữ liệu
        newProduct = productRepository.save(newProduct);

        return newProduct;
    }

    @Override
    @Transactional
    public Product updateProductImage(Long productId, String fileName) throws DataNotFoundException {
        // Tìm sản phẩm theo ID
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Không thể tìm thấy sản phẩm với id: " + productId));

        // Thêm URL hình ảnh vào cột img_url của sản phẩm
        existingProduct.setImg(fileName);
        return productRepository.save(existingProduct);
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest) {
        //lâý danh sách sản phẩm theo page và limit
        Page<Product> productPage;
        productPage = productRepository.searchProducts(categoryId, keyword, pageRequest);
        return productPage.map(ProductResponse::fromProduct);
    }

    @Override
    public Product addTagToProduct(Long productId, Long tagId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // Kiểm tra xem sản phẩm đã có tag chưa
        if (product.getTags().contains(tag)) {
            throw new IllegalArgumentException("Product already has this tag");
        }

        // Thêm tag vào sản phẩm
        product.getTags().add(tag);

        // Lưu sản phẩm và trả về
        return productRepository.save(product);
    }

}
