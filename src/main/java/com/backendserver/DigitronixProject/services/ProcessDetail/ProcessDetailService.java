package com.backendserver.DigitronixProject.services.ProcessDetail;

import com.backendserver.DigitronixProject.dtos.HandleProcessDetailDTO;
import com.backendserver.DigitronixProject.dtos.ProcessDetailDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Material;
import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.models.ProcessDetail;
import com.backendserver.DigitronixProject.repositories.MaterialRepository;
import com.backendserver.DigitronixProject.repositories.ProcessDetailRepository;
import com.backendserver.DigitronixProject.repositories.ProcessRepository;
import com.backendserver.DigitronixProject.responses.ProcessDetailResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProcessDetailService implements IProcessDetailService {
    private final ProcessDetailRepository processDetailRepository;
    private final ProcessRepository processRepository;
    private final MaterialRepository materialRepository;

    @SneakyThrows
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

//    @Override
//    public ProcessDetailResponse createProcessDetail(ProcessDetailDTO processDetailDTO) throws Exception {
//        Process findProcess = processRepository.findById(processDetailDTO.getProcessId()).orElseThrow(() ->  new DataNotFoundException("Cannot find this process!"));
//        Optional<ProcessDetail> checkProcessDetail = processDetailRepository.findByDetailName(processDetailDTO.getDetailName());
//        if(checkProcessDetail.isPresent()){
//            throw new Exception("This process detail is existed in application!");
//        }
//        ProcessDetail newProcessDetail = new ProcessDetail();
//        newProcessDetail.setDetailName(processDetailDTO.getDetailName());
//        newProcessDetail.setIntensity(processDetailDTO.getIntensity());
//        newProcessDetail.setProcess(findProcess);
//        newProcessDetail.setIsFinal(processDetailDTO.getIsFinal());
//        newProcessDetail.setInMaterialId(processDetailDTO.getInMaterialId());
//        newProcessDetail.setOutId(processDetailDTO.getOutId());
//
//        newProcessDetail = processDetailRepository.save(newProcessDetail);
//        return ProcessDetailResponse.fromProcessDetail(newProcessDetail);
//    }

    @Override
    public ProcessDetailResponse createProcessDetail(ProcessDetailDTO processDetailDTO) throws Exception {
        // Tìm process dựa trên processId
        Process findProcess = processRepository.findById(processDetailDTO.getProcessId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this process!"));
        Material findExistingMaterial = materialRepository.findById(processDetailDTO.getInMaterialId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this material in system!"));

        // Kiểm tra xem process detail đã tồn tại chưa
        Optional<ProcessDetail> checkProcessDetail = processDetailRepository.findByDetailName(processDetailDTO.getDetailName());
        if (checkProcessDetail.isPresent()) {
            throw new Exception("This process detail already exists in the application!");
        }

        // Tạo một ProcessDetail mới
        ProcessDetail newProcessDetail = new ProcessDetail();
        newProcessDetail.setDetailName(processDetailDTO.getDetailName());
        newProcessDetail.setIntensity(processDetailDTO.getIntensity());
        newProcessDetail.setProcess(findProcess);
        newProcessDetail.setIsFinal(processDetailDTO.getIsFinal());
        newProcessDetail.setMaterial(findExistingMaterial);
        newProcessDetail.setOutId(processDetailDTO.getOutId());

        // Lưu ProcessDetail mới vào cơ sở dữ liệu
        newProcessDetail = processDetailRepository.save(newProcessDetail);

        // Duyệt qua tất cả các ProcessDetail thuộc cùng process để cập nhật isFinal
        List<ProcessDetail> allProcessDetails = processDetailRepository.findProcessDetailByProcessId(findProcess.getId());
        int maxIntensity = Math.toIntExact(processDetailDTO.getIntensity()); // Giả sử intensity của process detail mới là lớn nhất

        for (ProcessDetail processDetail : allProcessDetails) {
            if (processDetail.getIntensity() != null) {
                if (processDetail.getIntensity() > maxIntensity) {
                    maxIntensity = Math.toIntExact(processDetail.getIntensity());
                }
            }
        }

        // Duyệt qua lại để cập nhật isFinal
        for (ProcessDetail processDetail : allProcessDetails) {
            if (processDetail.getIntensity() != null) {
                if (processDetail.getIntensity() == maxIntensity) {
                    processDetail.setIsFinal(true);
                } else {
                    processDetail.setIsFinal(false);
                }
                processDetailRepository.save(processDetail);
            }
        }

        return ProcessDetailResponse.fromProcessDetail(newProcessDetail);
    }

    @Override
    public ProcessDetailResponse updateProcessDetail(Long id, ProcessDetailDTO processDetailDTO) throws Exception {
        ProcessDetail checkExistingProcessDetail = processDetailRepository.getReferenceById(id);
        Process checkProcess = processRepository.findById(processDetailDTO.getProcessId()).orElseThrow(() -> new DataNotFoundException("Cannot find this process"));

        Material findExistingMaterial = materialRepository.findById(processDetailDTO.getInMaterialId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this material in system!"));

        checkExistingProcessDetail.setDetailName(processDetailDTO.getDetailName());
        checkExistingProcessDetail.setIntensity(processDetailDTO.getIntensity());
        checkExistingProcessDetail.setProcess(checkProcess);
        checkExistingProcessDetail.setIsFinal(processDetailDTO.getIsFinal());
        checkExistingProcessDetail.setMaterial(findExistingMaterial);
        checkExistingProcessDetail.setOutId(processDetailDTO.getOutId());
        processDetailRepository.save(checkExistingProcessDetail);
        return ProcessDetailResponse.fromProcessDetail(checkExistingProcessDetail);
    }

    @Override
    public String switchProcessIntensity(Long id, HandleProcessDetailDTO handleProcessDetailDTO) throws Exception{
        ProcessDetail checkExist = processDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find this process detail!"));
        if(checkExist.getIntensity() == null){
            throw new DataNotFoundException("Cannot find intensity of this process detail!");
        }
        ProcessDetail findAnotherProcessWithIntensity = processDetailRepository.findByIntensity(handleProcessDetailDTO.getIntensity());
        Long varSwitchIntensityBefore = checkExist.getIntensity();
        if(findAnotherProcessWithIntensity != null){
            checkExist.setIntensity(findAnotherProcessWithIntensity.getIntensity());
            findAnotherProcessWithIntensity.setIntensity(varSwitchIntensityBefore);
            List<ProcessDetail> processDetailList = processDetailRepository.findProcessDetailByProcessId(checkExist.getProcess().getId());
            for (ProcessDetail processDetail : processDetailList) {
                processDetail.setIsFinal(false);
                // Lưu các thay đổi vào cơ sở dữ liệu
                processDetailRepository.save(processDetail);
            }
        }else{
            checkExist.setIntensity(handleProcessDetailDTO.getIntensity());
            List<ProcessDetail> processDetailList = processDetailRepository.findProcessDetailByProcessId(checkExist.getProcess().getId());
            for (ProcessDetail processDetail : processDetailList) {
                processDetail.setIsFinal(false);
                // Lưu các thay đổi vào cơ sở dữ liệu
                processDetailRepository.save(processDetail);
            }
        }
        return "Switch intensity success!";
    }

    @Override
    public String setIsFinal(Long id) {
        List<ProcessDetail> processDetailList = processDetailRepository.findProcessDetailByProcessId(id);

        // Khởi tạo biến lưu trữ intensity cao nhất và index của ProcessDetail có intensity cao nhất
        Long maxIntensity = Long.MIN_VALUE;
        int maxIntensityIndex = -1;

        // Duyệt qua danh sách để tìm intensity cao nhất
        for (int i = 0; i < processDetailList.size(); i++) {
            ProcessDetail processDetail = processDetailList.get(i);
            Long intensity = processDetail.getIntensity();

            // So sánh intensity hiện tại với intensity cao nhất đã tìm thấy trước đó
            if (intensity != null && intensity > maxIntensity) {
                maxIntensity = intensity;
                maxIntensityIndex = i;
            }
        }

        // Đặt isFinal của ProcessDetail có intensity cao nhất là true và lưu vào cơ sở dữ liệu
        if (maxIntensityIndex != -1) {
            ProcessDetail processDetailWithMaxIntensity = processDetailList.get(maxIntensityIndex);
            processDetailWithMaxIntensity.setIsFinal(true);
            processDetailRepository.save(processDetailWithMaxIntensity);
        }

        return "Set isFinal success!";
    }

    @Override
    public void deleteProcessDetail(Long id) throws Exception {
        ProcessDetail checkExist = processDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("cannot find this process detail!"));
        processDetailRepository.delete(checkExist);
    }
}
