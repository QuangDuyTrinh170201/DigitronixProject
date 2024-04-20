package com.backendserver.DigitronixProject.services.DataAccess;

import com.backendserver.DigitronixProject.dtos.DataAccessDTO;
import com.backendserver.DigitronixProject.models.DataAccess;
import com.backendserver.DigitronixProject.repositories.DataAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DataAccessService implements IDataAccessService {
    private final DataAccessRepository dataAccessRepository;
    @Override
    public DataAccess createDataAccess(DataAccessDTO dataAccessDTO) {
        DataAccess newData = DataAccess.builder()
                .productId(dataAccessDTO.getProductId())
                .productName(dataAccessDTO.getProductName())
                .productQuantity(dataAccessDTO.getProductQuantity())
                .status(dataAccessDTO.isStatus())
                .build();
        return dataAccessRepository.save(newData);
    }

    @Override
    public DataAccess getById(Long id) {
        return dataAccessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found"));
    }

    @Override
    public List<DataAccess> getAll() {
        return dataAccessRepository.findAll();
    }

    @Override
    public String deleteAll(){
        dataAccessRepository.deleteAll();
        return "All data have been deleted!";
    }
}
