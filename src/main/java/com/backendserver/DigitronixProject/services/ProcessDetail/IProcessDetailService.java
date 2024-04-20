package com.backendserver.DigitronixProject.services.ProcessDetail;

import com.backendserver.DigitronixProject.dtos.HandleProcessDetailDTO;
import com.backendserver.DigitronixProject.dtos.ProcessDetailDTO;
import com.backendserver.DigitronixProject.responses.ProcessDetailResponse;

import java.util.List;

public interface IProcessDetailService {
    List<ProcessDetailResponse> getAllProcessDetail() throws Exception;

    ProcessDetailResponse getProcessDetailId(Long id) throws Exception;

    ProcessDetailResponse createProcessDetail(ProcessDetailDTO processDetailDTO) throws Exception;

    ProcessDetailResponse updateProcessDetail(Long id, ProcessDetailDTO processDetailDTO) throws Exception;

    void deleteProcessDetail(Long id) throws Exception;

    String switchProcessIntensity(Long id, HandleProcessDetailDTO handleProcessDetailDTO) throws Exception;

    String setIsFinal(Long id);
}
