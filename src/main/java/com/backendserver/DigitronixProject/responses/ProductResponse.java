package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.backendserver.DigitronixProject.models.Tag;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private Double price;
    private String img;
    private int quantity;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("tags")
    private List<String> tags;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .img(product.getImg())
                .quantity(product.getQuantity())
                .tags(product.getTags().stream()
                        .map(Tag::getTagName)
                        .collect(Collectors.toList()))
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
