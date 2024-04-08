package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Material;
import com.backendserver.DigitronixProject.models.Product;
import com.backendserver.DigitronixProject.models.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialResponse extends BaseResponse{
    private Long id;
    private String name;
    private Double price;
    private String image;
    private Long quantity;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("tags")
    private List<String> tags;

    public static MaterialResponse fromMaterial(Material material) {
        MaterialResponse materialResponse = null;
        materialResponse = MaterialResponse.builder()
                .id(material.getId())
                .name(material.getName())
                .price(material.getPrice())
                .categoryId(material.getCategory().getId())
                .categoryName(material.getCategory().getName())
                .image(material.getImage())
                .quantity(material.getQuantity())
                .tags(material.getTags().stream()
                        .map(Tag::getTagName)
                        .collect(Collectors.toList()))
                .build();
        materialResponse.setCreatedAt(material.getCreatedAt());
        materialResponse.setUpdatedAt(material.getUpdatedAt());
        return materialResponse;
    }
}
