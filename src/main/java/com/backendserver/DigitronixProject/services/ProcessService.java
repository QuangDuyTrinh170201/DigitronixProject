package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.ProcessDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.models.Product;
import com.backendserver.DigitronixProject.repositories.ProcessRepository;
import com.backendserver.DigitronixProject.repositories.ProductRepository;
import com.backendserver.DigitronixProject.responses.ProcessResponse;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProcessService implements IProcessService{
    private final ProcessRepository processRepository;
    private final ProductRepository productRepository;
    @Override
    public List<ProcessResponse> getAllProcess() throws Exception {
        List<Process> listAllProcess = processRepository.findAll();
        if(listAllProcess.isEmpty()){
            throw new DataNotFoundException("Empty material in database!");
        }
        return listAllProcess.stream().map(ProcessResponse::fromProcess).toList();
    }

    @Override
    public Optional<ProcessResponse> getProcessById(Long id) throws Exception {
        Process existingProcess = processRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException("Cannot find this process, please check again"));
        return Optional.of(ProcessResponse.fromProcess(existingProcess));
    }

    @Override
    public ProcessResponse createProcess(ProcessDTO processDTO) throws DataNotFoundException {
        Product checkProduct = productRepository.findById(processDTO.getProductId()).orElseThrow(() -> new DataNotFoundException("Cannot find this product in application!"));
        Process newProcess = new Process();
        newProcess.setProcessName(processDTO.getProcessName());
        newProcess.setProduct(checkProduct);

        newProcess = processRepository.save(newProcess);
        return ProcessResponse.fromProcess(newProcess);
    }

    @Override
    public ProcessResponse updateProcess(Long id, ProcessDTO processDTO) throws Exception {
        Process existingProcess = processRepository.getReferenceById(id);
        if(existingProcess.getProcessName() != null){
            existingProcess.setProcessName(processDTO.getProcessName());
        }
        if(existingProcess.getProduct() != null){
            Product existingProduct = productRepository.findById(processDTO.getProductId()).orElseThrow(() -> new DataIntegrityViolationException("Cannot find this product in application!"));
            existingProcess.setProduct(existingProduct);
        }
        return ProcessResponse.fromProcess(processRepository.save(existingProcess));
    }

    @Override
    public void deleteProcess(Long id) throws Exception {
        Process findDel = processRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException("Cannot find this process!"));
        processRepository.delete(findDel);
    }
}
