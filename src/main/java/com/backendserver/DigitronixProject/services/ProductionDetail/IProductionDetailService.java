package com.backendserver.DigitronixProject.services.ProductionDetail;

import com.backendserver.DigitronixProject.dtos.ProductionDetailDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.responses.ProductionDetailResponse;

import java.util.List;

public interface IProductionDetailService {
    List<ProductionDetailResponse> getAllProductionDetail();

    ProductionDetailResponse getProductionDetailById(Long id) throws Exception;

    List<ProductionDetailResponse> getProductionDetailByUserId(Long id) throws Exception;

    ProductionDetailResponse createProductionDetail(ProductionDetailDTO productionDetailDTO) throws Exception;

    ProductionDetailResponse editProductionDetail(Long id, ProductionDetailDTO productionDetailDTO) throws DataNotFoundException;

    void deleteProductionDetail (Long id)throws Exception;
}
