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
public class TagResponse extends BaseResponse {
    private Long id;
    private String name;

    @JsonProperty("products")
    private List<ProductResponse> products;

    public static TagResponse fromTag(Tag tag) {
        TagResponse tagResponse = TagResponse.builder()
                .id(tag.getId())
                .name(tag.getTagName())
                .products(tag.getProducts().stream()
                        .map(ProductResponse::fromProduct)
                        .collect(Collectors.toList()))
                .build();
        tagResponse.setCreatedAt(tag.getCreatedAt());
        tagResponse.setUpdatedAt(tag.getUpdatedAt());
        return tagResponse;
    }
}
