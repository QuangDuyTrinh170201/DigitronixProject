package com.backendserver.DigitronixProject.services.Product;

import com.backendserver.DigitronixProject.dtos.ProductDTO;
import com.backendserver.DigitronixProject.dtos.ProductUpdateDTO;
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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    @Override
    @Transactional
    public Product createProductInfo(ProductDTO productDTO) throws DataNotFoundException, InvalidParameterException {
        // Tìm danh mục sản phẩm tương ứng
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Không thể tìm thấy danh mục sản phẩm với id: " + productDTO.getCategoryId()));


        // Tạo sản phẩm mới
        Product newProduct = new Product();
        newProduct.setProductName(productDTO.getProductName());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setQuantity(productDTO.getQuantity());
        newProduct.setCategory(existingCategory);
        newProduct.setMissing(0);

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
    public List<ProductResponse> getAllProductNoPaging() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(ProductResponse::fromProduct).toList();
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

    @Override
    public String deleteProduct(Long productId) throws DataNotFoundException {
        Optional<Product> findProduct = productRepository.findById(productId);
        if(findProduct.isEmpty()){
            throw new DataNotFoundException("Cannot find this product!");
        }
        if(findProduct.get().getImg() == null){
            findProduct.ifPresent(productRepository::delete);
            return "Delete Successfully, but the application cannot find image!";
        }else{
            Path deleteFile = Paths.get("uploads/", findProduct.get().getImg());
            File file = new File(String.valueOf(deleteFile));
            file.delete();
            findProduct.ifPresent(productRepository::delete);
            return "Delete product successfully";
        }
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateDTO productDTO) throws DataNotFoundException{
        Product existingProduct = productRepository.getById(id);

        String oldImage = existingProduct.getImg();
        existingProduct.setProductName(productDTO.getProductName());
        if(productDTO.getPrice() != null){
            existingProduct.setPrice(productDTO.getPrice());
        }
        if(productDTO.getCategoryId() != null){
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find the category of this product with id " +productDTO.getCategoryId()));
            existingProduct.setCategory(existingCategory);
        }
        if(productDTO.getImg() != null){
            existingProduct.setImg(oldImage);
        }
        if(existingProduct.getMissing() < 0){
            int quantityBeforeInput = productDTO.getQuantity();
            int missingBeforeInput = existingProduct.getMissing();
            int quantityAfterInput = quantityBeforeInput + missingBeforeInput;
            if(quantityAfterInput < 0){
                existingProduct.setMissing(quantityAfterInput);
                existingProduct.setQuantity(0);
            }else if(quantityAfterInput > 0){
                existingProduct.setQuantity(quantityAfterInput);
                existingProduct.setMissing(0);
            }else{
                existingProduct.setMissing(0);
                existingProduct.setQuantity(0);
            }
        }
        productRepository.save(existingProduct);
        return ProductResponse.fromProduct(existingProduct);
    }


    @Override
    public Product getProductById(long productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find product with id =" + productId);
    }
}
