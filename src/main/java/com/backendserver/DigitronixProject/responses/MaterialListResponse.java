package com.backendserver.DigitronixProject.responses;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialListResponse {
    List<MaterialResponse> materials;
    private int totalPages;
}
