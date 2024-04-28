package com.backendserver.DigitronixProject.services.DataAccess;

import com.backendserver.DigitronixProject.dtos.DataAccessDTO;
import com.backendserver.DigitronixProject.dtos.MaterialDataAccessDTO;
import com.backendserver.DigitronixProject.models.DataAccess;
import com.backendserver.DigitronixProject.models.MaterialDataAccess;
import com.backendserver.DigitronixProject.responses.MaterialDataAccessResponse;

import java.io.IOException;
import java.util.List;

public interface IDataAccessService {
    DataAccess createDataAccess(DataAccessDTO dataAccessDTO);

    DataAccess getById(Long id);

    List<DataAccess> getAll();

    String deleteAll();

    public byte[] exportDataAsExcel() throws IOException;

    MaterialDataAccessResponse createMaterialDataAccess(MaterialDataAccessDTO materialDataAccessDTO) throws Exception;

    List<MaterialDataAccessResponse> getAllMaterialDataAccess();

    MaterialDataAccessResponse getMaterialDataAccessById(Long id) throws Exception;

    void deleteAllMaterialDataAccess();

    public byte[] exportMaterialDataAccessAsExcel() throws IOException;
}
