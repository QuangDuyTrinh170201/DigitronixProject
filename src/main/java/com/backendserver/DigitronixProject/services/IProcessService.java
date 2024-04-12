package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.ProcessDTO;
import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.responses.ProcessResponse;

import java.util.List;
import java.util.Optional;

public interface IProcessService {
    List<ProcessResponse> getAllProcess() throws Exception;

    Optional<ProcessResponse> getProcessById(Long id) throws Exception;

    ProcessResponse createProcess(ProcessDTO processDTO) throws Exception;

    ProcessResponse updateProcess(Long id, ProcessDTO processDTO) throws Exception;

    void deleteProcess(Long id) throws Exception;
}
