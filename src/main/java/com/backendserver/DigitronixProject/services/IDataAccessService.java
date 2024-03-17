package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.DataAccessDTO;
import com.backendserver.DigitronixProject.models.DataAccess;

import java.util.List;

public interface IDataAccessService {
    DataAccess createDataAccess(DataAccessDTO dataAccessDTO);

    DataAccess getById(Long id);

    List<DataAccess> getAll();

    String deleteAll();
}
