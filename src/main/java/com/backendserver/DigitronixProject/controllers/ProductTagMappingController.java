package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.ProductTagMappingDTO;
import com.backendserver.DigitronixProject.models.Product;
import com.backendserver.DigitronixProject.models.Tag;
import com.backendserver.DigitronixProject.services.IProductTagMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/product_tags_mapping")
public class ProductTagMappingController {
    private final IProductTagMappingService productTagMappingService;
    @DeleteMapping("/{productId}/tags/{tagId}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<String> removeTagOrProduct(@PathVariable Product productId, @PathVariable Tag tagId){
        Long prodId = productId.getId();
        Long tagsId = tagId.getId();
        productTagMappingService.removeTagOrProduct(prodId, tagsId);
        return ResponseEntity.ok("Remove tag/product successfully");
    }
}
