package com.backendserver.DigitronixProject.services.Production;

import com.backendserver.DigitronixProject.dtos.ProductionDTO;
import com.backendserver.DigitronixProject.responses.ProductionResponse;

import java.util.List;

public interface IProductionService {
    List<ProductionResponse> getAllProduction()throws Exception;

    ProductionResponse getProductionWithId(Long id) throws Exception;

    ProductionResponse createProduction(ProductionDTO productionDTO) throws Exception;

    ProductionResponse updateProduction(Long id, ProductionDTO productionDTO) throws Exception;

    List<ProductionResponse> getProductionWithOrderId(Long id) throws Exception;

    List<ProductionResponse> getProductionWithUserId(Long id) throws Exception;

    void deleteProduction(Long id) throws Exception;
}
