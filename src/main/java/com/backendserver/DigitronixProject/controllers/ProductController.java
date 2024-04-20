package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.ProductDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Product;
import com.backendserver.DigitronixProject.responses.ProductListResponse;
import com.backendserver.DigitronixProject.responses.ProductResponse;
import com.backendserver.DigitronixProject.services.Product.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping(value = "/create/info")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> createProductInfo(@Valid @RequestBody ProductDTO productDTO) {
        try {
            Product newProduct = productService.createProductInfo(productDTO);
            return ResponseEntity.ok().body(newProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "/{productId}/image")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> updateProductImage(@PathVariable("productId") Long productId,
                                                @ModelAttribute MultipartFile file) {
        try {
            String imageUpdate = storeFile(file);
            Product updatedProduct = productService.updateProductImage(productId, imageUpdate);
            return ResponseEntity.ok().body("Import image successfully!");
        } catch (DataNotFoundException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đến file đích
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_SALE', 'ROLE_PRODUCTION_MANAGER', 'ROLE_INVENTORY_MANAGER', 'ROLE_WORKER', 'ROLE_DRIVER')")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit){
//        logger.info(String.format("keyword = %s, category_id = %d, page = %d, limit = %d", keyword, categoryId, page, limit));
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
        Page<ProductResponse> productPage;
        productPage = productService.getAllProducts(keyword, categoryId, pageRequest);
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try{
            Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if(resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else{
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpg").toUri()));
            }
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allNoPaging")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_SALE', 'ROLE_PRODUCTION_MANAGER', 'ROLE_INVENTORY_MANAGER', 'ROLE_WORKER', 'ROLE_DRIVER')")
    public ResponseEntity<?> getAllProductNoPaging(){
        try{
            List<ProductResponse> productResponse = productService.getAllProductNoPaging();
            return ResponseEntity.ok(productResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{productId}/tags/{tagId}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<String> addTagToProduct(@PathVariable Long productId, @PathVariable Long tagId) {
        Product updatedProduct = productService.addTagToProduct(productId, tagId);
        return ResponseEntity.ok().body("Add Tag Successfully");
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try{
            String message = productService.deleteProduct(productId);
            return ResponseEntity.ok(message);
        }catch (DataNotFoundException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductDTO productDTO){
        try{
            ProductResponse updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_SALE', 'ROLE_PRODUCTION_MANAGER', 'ROLE_INVENTORY_MANAGER', 'ROLE_WORKER', 'ROLE_DRIVER')")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId){
        try {
            Product existingProduct = productService.getProductById(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

