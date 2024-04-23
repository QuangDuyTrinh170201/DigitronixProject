package com.backendserver.DigitronixProject.services.ProductionDetail;

import com.backendserver.DigitronixProject.dtos.ProductionDetailDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.*;
import com.backendserver.DigitronixProject.repositories.*;
import com.backendserver.DigitronixProject.responses.ProductionDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductionDetailService implements IProductionDetailService{
    private final ProductionDetailRepository productionDetailRepository;
    private final ProductionRepository productionRepository;
    private final ProcessDetailRepository processDetailRepository;
    private final UserRepository userRepository;
    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;
    @Override
    public List<ProductionDetailResponse> getAllProductionDetail() {
        List<ProductionDetail> productionDetails = productionDetailRepository.findAll();
        return productionDetails.stream().map(ProductionDetailResponse::fromProductionDetail).toList();
    }

    @Override
    public ProductionDetailResponse getProductionDetailById(Long id) throws Exception {
        ProductionDetail productionDetail = productionDetailRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Cannot find any production detail with this id!"));
        return ProductionDetailResponse.fromProductionDetail(productionDetail);
    }

    @Override
    public ProductionDetailResponse createProductionDetail(ProductionDetailDTO productionDetailDTO) throws Exception {
        ProcessDetail findExistingProcessDetail = processDetailRepository.findById(productionDetailDTO.getProcessDetailId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find this process in application!"));
        Production findExistingProduction = productionRepository.findById(productionDetailDTO.getProductionId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find this production in application!"));
        User findExistingUser = userRepository.findById(productionDetailDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this user in application!"));
        List<ProductionDetail> checkDuplicateProduction = productionDetailRepository.findProductionDetailByProductionId(productionDetailDTO.getProductionId());
        for(ProductionDetail productionDetail : checkDuplicateProduction){
            if(Objects.equals(productionDetailDTO.getName(), productionDetail.getName())){
                throw new DataNotFoundException("Production detail with this name has existed in system, please check again!");
            }
        }
        List<ProductionDetail> productionDetailList = productionDetailRepository.findAll();
        for(ProductionDetail productionDetail1 : productionDetailList){
            if(Objects.equals(productionDetailDTO.getProductionId(), productionDetail1.getProduction().getId()) && Objects.equals(productionDetailDTO.getProcessDetailId(), productionDetail1.getProcessDetail().getId())){
                throw new DataIntegrityViolationException("Cannot add a production detail with have the same production and process with another production detail!");
            }
        }
        float varProductionDetailCost;
        Material getMaterialPrice = materialRepository.findById(findExistingProcessDetail.getInMaterialId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this material in system!"));
        Double priceOfMaterial = getMaterialPrice.getPrice();
        varProductionDetailCost = (float) (priceOfMaterial * productionDetailDTO.getInMaterialQuantity());

        ProductionDetail newProductionDetail = new ProductionDetail();
        newProductionDetail.setName(productionDetailDTO.getName());
        newProductionDetail.setStatus("todo");
        newProductionDetail.setCost(varProductionDetailCost);
        newProductionDetail.setProcessDetail(findExistingProcessDetail);
        newProductionDetail.setProduction(findExistingProduction);
        newProductionDetail.setUser(findExistingUser);
        newProductionDetail.setInMaterialQuantity(productionDetailDTO.getInMaterialQuantity());
        newProductionDetail.setOutQuantity(productionDetailDTO.getOutQuantity());
        newProductionDetail = productionDetailRepository.save(newProductionDetail);
        return ProductionDetailResponse.fromProductionDetail(newProductionDetail);
    }

    @Override
    public ProductionDetailResponse editProductionDetail(Long id, ProductionDetailDTO productionDetailDTO) throws DataNotFoundException {
        ProductionDetail findExistingProductionDetail = productionDetailRepository.findById(id).orElseThrow(()->new DataNotFoundException("Cannot find production detail in system!"));
        ProcessDetail findExistingProcessDetail = processDetailRepository.findById(productionDetailDTO.getProcessDetailId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find this process in application!"));
        Production findExistingProduction = productionRepository.findById(productionDetailDTO.getProductionId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find this production in application!"));
        User findExistingUser = userRepository.findById(productionDetailDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this user in application!"));
        if(productionDetailDTO.getStatus().equals("pending")){
            findExistingProductionDetail.setTimeStart(productionDetailDTO.getTimeStart());
        }
        else if(productionDetailDTO.getStatus().equals("done")){
            if(findExistingProcessDetail.getIsFinal().equals(true)){
                Product existingProduct = productRepository.findById(findExistingProcessDetail.getOutId())
                        .orElseThrow(()->new DataNotFoundException("Cannot find any product matching with this production detail!"));
                int prodQuantity = existingProduct.getQuantity();
                int prodMissing = existingProduct.getMissing();
                int productInWarehouseAfterProduce = productionDetailDTO.getOutQuantity() + prodQuantity + prodMissing;
                if(productInWarehouseAfterProduce > 0){
                    existingProduct.setMissing(0);
                    existingProduct.setQuantity(productInWarehouseAfterProduce);
                }else {
                    existingProduct.setQuantity(0);
                    existingProduct.setMissing(productInWarehouseAfterProduce);
                }
            }
            findExistingProductionDetail.setTimeEnd(productionDetailDTO.getTimeEnd());
        }
        findExistingProductionDetail.setName(productionDetailDTO.getName());
        findExistingProductionDetail.setStatus(productionDetailDTO.getStatus());
        findExistingProductionDetail.setCost(productionDetailDTO.getCost());
        findExistingProductionDetail.setProcessDetail(findExistingProcessDetail);
        findExistingProductionDetail.setProduction(findExistingProduction);
        findExistingProductionDetail.setUser(findExistingUser);
        return ProductionDetailResponse.fromProductionDetail(productionDetailRepository.save(findExistingProductionDetail));
    }

    @Override
    public void deleteProductionDetail(Long id) throws Exception {

    }
}
