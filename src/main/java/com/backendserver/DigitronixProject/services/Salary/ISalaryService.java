package com.backendserver.DigitronixProject.services.Salary;

import com.backendserver.DigitronixProject.dtos.SalaryDTO;
import com.backendserver.DigitronixProject.responses.SalaryResponse;

import java.util.List;

public interface ISalaryService {
    List<SalaryResponse> getAllSalary() throws Exception;

    SalaryResponse getSalaryById(Long id) throws Exception;

    List<SalaryResponse> getSalaryByUserId(Long id) throws Exception;

    SalaryResponse addNewSalary(SalaryDTO salaryDTO) throws Exception;

    SalaryResponse updateSalary(Long id, SalaryDTO salaryDTO) throws Exception;

    byte[] setNewMonth() throws Exception;

    void timekeepingAll() throws Exception;

    void deleteSalary(Long id) throws Exception;
}
