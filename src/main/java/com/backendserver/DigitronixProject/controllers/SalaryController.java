package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.SalaryDTO;
import com.backendserver.DigitronixProject.responses.SalaryResponse;
import com.backendserver.DigitronixProject.services.Salary.ISalaryService;
import com.backendserver.DigitronixProject.services.Salary.SalaryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/salaries")
@PreAuthorize("hasRole('ROLE_DIRECTOR')")
public class SalaryController {
    private final ISalaryService salaryService;
    @GetMapping("")
    public ResponseEntity<?> getAllSalary(){
        try{
            List<SalaryResponse> salaryResponses = salaryService.getAllSalary();
            return ResponseEntity.ok(salaryResponses);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalaryWithId(@PathVariable Long id){
        try{
            SalaryResponse salaryResponse = salaryService.getSalaryById(id);
            return ResponseEntity.ok(salaryResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getSalaryWithUSerId(@PathVariable Long id){
        try{
            List<SalaryResponse> salaryResponse = salaryService.getSalaryByUserId(id);
            return ResponseEntity.ok(salaryResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createNewSalaryInfor(@RequestBody SalaryDTO salaryDTO){
        try{
            SalaryResponse salaryResponse = salaryService.addNewSalary(salaryDTO);
            return ResponseEntity.ok(salaryResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editSalary(@PathVariable Long id, @RequestBody SalaryDTO salaryDTO){
        try{
            SalaryResponse salaryResponse = salaryService.updateSalary(id, salaryDTO);
            return ResponseEntity.ok(salaryResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/NewMonth")
    public ResponseEntity<byte[]> createNewSalaryReportForNewMonth() {
        try {
            // Lấy tháng hiện tại
            LocalDate currentDate = LocalDate.now();
            String currentMonth = currentDate.format(DateTimeFormatter.ofPattern("MM-yyyy"));

            // Gọi phương thức setNewMonth từ service để tạo báo cáo lương mới cho tháng mới
            byte[] excelData = salaryService.setNewMonth();

            // Tạo tên file kết hợp với tháng hiện tại
            String filename = "salary_report_" + currentMonth + ".xlsx";

            // Tạo HttpHeaders để đặt tên file và kiểu dữ liệu của file Excel
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);

            // Trả về ResponseEntity chứa dữ liệu của file Excel và HttpHeaders
            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Xử lý các ngoại lệ và trả về lỗi nếu có
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/timekepping")
    public ResponseEntity<?> timekeepingAllSalaryReport(){
        try{
            salaryService.timekeepingAll();
            return ResponseEntity.ok("Set timekeeping all success!");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalary(@PathVariable Long id){
        try{
            salaryService.deleteSalary(id);
            return ResponseEntity.ok("Delete salary successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
