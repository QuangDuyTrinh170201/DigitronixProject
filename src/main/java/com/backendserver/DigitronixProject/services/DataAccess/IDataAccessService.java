package com.backendserver.DigitronixProject.services.DataAccess;

import com.backendserver.DigitronixProject.dtos.DataAccessDTO;
import com.backendserver.DigitronixProject.models.DataAccess;

import java.io.IOException;
import java.util.List;

public interface IDataAccessService {
    DataAccess createDataAccess(DataAccessDTO dataAccessDTO);

    DataAccess getById(Long id);

    List<DataAccess> getAll();

    String deleteAll();

    public byte[] exportDataAsExcel() throws IOException;
}
