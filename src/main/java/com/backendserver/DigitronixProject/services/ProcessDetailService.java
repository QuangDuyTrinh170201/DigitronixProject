package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.ProcessDetailDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.models.ProcessDetail;
import com.backendserver.DigitronixProject.repositories.ProcessDetailRepository;
import com.backendserver.DigitronixProject.repositories.ProcessRepository;
import com.backendserver.DigitronixProject.responses.ProcessDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProcessDetailService implements IProcessDetailService{
    private final ProcessDetailRepository processDetailRepository;
    private final ProcessRepository processRepository;

    @Override
    public List<ProcessDetailResponse> getAllProcessDetail() throws Exception {
        List<ProcessDetail> processDetailList = processDetailRepository.findAll();
        return processDetailList.stream().map(ProcessDetailResponse::fromProcessDetail).toList();
    }

    @Override
    public ProcessDetailResponse getProcessDetailId(Long id) throws Exception {
        ProcessDetail findProcessDetail = processDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find this process detail!"));
        return ProcessDetailResponse.fromProcessDetail(findProcessDetail);
    }

    @Override
    public ProcessDetailResponse createProcessDetail(ProcessDetailDTO processDetailDTO) throws Exception {
        Process findProcess = processRepository.findById(processDetailDTO.getProcessId()).orElseThrow(() ->  new DataNotFoundException("Cannot find this process!"));
        Optional<ProcessDetail> checkProcessDetail = processDetailRepository.findByDetailName(processDetailDTO.getDetailName());
        if(checkProcessDetail.isPresent()){
            throw new Exception("This process detail is existed in application!");
        }
        ProcessDetail newProcessDetail = new ProcessDetail();
        newProcessDetail.setDetailName(processDetailDTO.getDetailName());
        newProcessDetail.setIntensity(processDetailDTO.getIntensity());
        newProcessDetail.setProcess(findProcess);

        newProcessDetail = processDetailRepository.save(newProcessDetail);
        return ProcessDetailResponse.fromProcessDetail(newProcessDetail);
    }

    @Override
    public ProcessDetailResponse updateProcessDetail(Long id, ProcessDetailDTO processDetailDTO) throws Exception {
        ProcessDetail checkExistingProcessDetail = processDetailRepository.getReferenceById(id);
        Process checkProcess = processRepository.findById(processDetailDTO.getProcessId()).orElseThrow(() -> new DataNotFoundException("Cannot find this process"));

        checkExistingProcessDetail.setDetailName(processDetailDTO.getDetailName());
        checkExistingProcessDetail.setIntensity(processDetailDTO.getIntensity());
        checkExistingProcessDetail.setProcess(checkProcess);
        processDetailRepository.save(checkExistingProcessDetail);
        return ProcessDetailResponse.fromProcessDetail(checkExistingProcessDetail);
    }

    @Override
    public void deleteProcessDetail(Long id) throws Exception {
        ProcessDetail checkExist = processDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("cannot find this process detail!"));
        processDetailRepository.delete(checkExist);
    }
}
