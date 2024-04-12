package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.backendserver.DigitronixProject.models.Tag;
import org.springframework.core.io.UrlResource;

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

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("tags")
    private List<TagMapResponse> tags;


    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = null;
            productResponse = ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getProductName())
                    .price(product.getPrice())
                    .categoryId(product.getCategory().getId())
                    .categoryName(product.getCategory().getName())
                    .img(product.getImg())
                    .quantity(product.getQuantity())
                    .tags(product.getTags().stream()
                            .map(tag -> {
                                TagMapResponse tagResponse = new TagMapResponse();
                                tagResponse.setId(tag.getId());
                                tagResponse.setName(tag.getTagName());
                                return tagResponse;
                            })
                            .collect(Collectors.toList()))
                    .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}

