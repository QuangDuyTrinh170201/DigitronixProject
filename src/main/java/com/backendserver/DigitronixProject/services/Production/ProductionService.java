package com.backendserver.DigitronixProject.services.Production;

import com.backendserver.DigitronixProject.dtos.ProductionDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Order;
import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.models.Production;
import com.backendserver.DigitronixProject.models.User;
import com.backendserver.DigitronixProject.repositories.OrderRepository;
import com.backendserver.DigitronixProject.repositories.ProcessRepository;
import com.backendserver.DigitronixProject.repositories.ProductionRepository;
import com.backendserver.DigitronixProject.repositories.UserRepository;
import com.backendserver.DigitronixProject.responses.ProductionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionService implements IProductionService{
    private final ProductionRepository productionRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProcessRepository processRepository;

    @Override
    public List<ProductionResponse> getAllProduction() throws Exception {
        List<Production> productionList = productionRepository.findAll();
        return productionList.stream().map(ProductionResponse::fromProduction).toList();
    }

    @Override
    public ProductionResponse getProductionWithId(Long id) throws Exception {
        Production production = productionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find any production with this is!"));
        return ProductionResponse.fromProduction(production);
    }

    @Override
    public ProductionResponse createProduction(ProductionDTO productionDTO) throws Exception {
        User user = userRepository.findById(productionDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this user in application, please check again!"));
        Process process = processRepository.findById(productionDTO.getProcessId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this process in application, please check again"));
        Order order = orderRepository.findById(productionDTO.getOrderId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this order before, check again or contact with your administrator"));
        Production production = new Production();
        production.setTimeStart(productionDTO.getTimeStart());
        production.setTimeEnd(productionDTO.getTimeEnd());
        production.setQuantity(productionDTO.getQuantity());
        production.setStatus(productionDTO.getStatus());
        production.setTotalCost(productionDTO.getTotalCost());
        production.setUser(user);
        production.setProcess(process);
        production.setOrder(order);

        production = productionRepository.save(production);
        return ProductionResponse.fromProduction(production);
    }

    @Override
    public ProductionResponse updateProduction(Long id, ProductionDTO productionDTO) throws Exception {
        Production production = productionRepository.getReferenceById(id);
        User user = userRepository.findById(productionDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this user in application, please check again!"));
        Process process = processRepository.findById(productionDTO.getProcessId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this process in application, please check again"));
        Order order = orderRepository.findById(productionDTO.getOrderId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this order before, check again or contact with your administrator"));

        production.setTimeStart(productionDTO.getTimeStart());
        production.setTimeEnd(productionDTO.getTimeEnd());
        production.setQuantity(productionDTO.getQuantity());
        production.setStatus(productionDTO.getStatus());
        production.setTotalCost(productionDTO.getTotalCost());
        production.setUser(user);
        production.setProcess(process);
        production.setOrder(order);

        return ProductionResponse.fromProduction(productionRepository.save(production));
    }

    @Override
    public List<ProductionResponse> getProductionWithOrderId(Long id) throws Exception {
        List<Production> production = productionRepository.findProductionByOrderId(id);
        return production.stream().map(ProductionResponse::fromProduction).toList();
    }

    @Override
    public List<ProductionResponse> getProductionWithUserId(Long id) throws Exception {
        List<Production> productionList = productionRepository.findProductionByUserId(id);
        return productionList.stream().map(ProductionResponse::fromProduction).toList();
    }

    @Override
    public void deleteProduction(Long id) throws Exception{
        Production production = productionRepository.findById(id).orElseThrow(()->new DataNotFoundException("Cannot find this production, please check again!"));
        productionRepository.delete(production);
    }
}
