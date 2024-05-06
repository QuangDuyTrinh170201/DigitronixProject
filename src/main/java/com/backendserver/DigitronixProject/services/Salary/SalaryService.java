package com.backendserver.DigitronixProject.services.Salary;

import com.backendserver.DigitronixProject.dtos.SalaryDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Order;
import com.backendserver.DigitronixProject.models.ProductionDetail;
import com.backendserver.DigitronixProject.models.Salary;
import com.backendserver.DigitronixProject.models.User;
import com.backendserver.DigitronixProject.repositories.OrderRepository;
import com.backendserver.DigitronixProject.repositories.ProductionDetailRepository;
import com.backendserver.DigitronixProject.repositories.SalaryRepository;
import com.backendserver.DigitronixProject.repositories.UserRepository;
import com.backendserver.DigitronixProject.responses.SalaryResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class SalaryService implements ISalaryService{
    private final SalaryRepository salaryRepository;
    private final UserRepository userRepository;
    private final ProductionDetailRepository productionDetailRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<SalaryResponse> getAllSalary() throws Exception {
        List<Salary> salaryList = salaryRepository.findAll();
        return salaryList.stream().map(SalaryResponse::fromSalary).toList();
    }

    @Override
    public SalaryResponse getSalaryById(Long id) throws Exception {
        Salary salary = salaryRepository.findById(id).orElseThrow(()->new DataNotFoundException("Cannot find any salary with this id!"));
        return SalaryResponse.fromSalary(salary);
    }

    @Override
    public List<SalaryResponse> getSalaryByUserId(Long id) throws Exception {
        List<Salary> salary = salaryRepository.findSalaryByUserId(id);
        return salary.stream().map(SalaryResponse::fromSalary).toList();
    }

//    @Override
//    public SalaryResponse addNewSalary(SalaryDTO salaryDTO) throws Exception {
//        List<Salary> findExistingSalary = salaryRepository.findSalaryByUserId(salaryDTO.getUserId());
//        for(Salary salary : findExistingSalary){
//            if(salaryDTO.getUserId().equals(salary.getUser().getId())){
//                throw new DataIntegrityViolationException("Cannot add new salary for existing salary in system!");
//            }
//        }
//        User findExistingUser = userRepository.findById(salaryDTO.getUserId())
//                .orElseThrow(()->new DataNotFoundException("Cannot find this user in system!"));
//        List<ProductionDetail> productionDetailList = productionDetailRepository.findProductionDetailByUserId(findExistingUser.getId());
//
//
//        Salary salary = new Salary();
//        salary.setSalaryPerDate(salaryDTO.getSalaryPerDate());
//        salary.setWorkingDate(salaryDTO.getWorkingDate());
//        salary.setMinKpi(salaryDTO.getMinKpi());
//        salary.setRateSa(salaryDTO.getRateSa());
//
//        salary.setTotal(salaryDTO.getTotal());
//        salary.setUser(findExistingUser);
//
//        salary = salaryRepository.save(salary);
//        return SalaryResponse.fromSalary(salary);
//    }

    @Override
    public SalaryResponse addNewSalary(SalaryDTO salaryDTO) throws Exception {
        List<Salary> findExistingSalary = salaryRepository.findSalaryByUserId(salaryDTO.getUserId());
        for (Salary salary : findExistingSalary) {
            if (salaryDTO.getUserId().equals(salary.getUser().getId())) {
                throw new DataIntegrityViolationException("Cannot add new salary for existing salary in system!");
            }
        }
        User findExistingUser = userRepository.findById(salaryDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this user in system!"));
        List<ProductionDetail> productionDetailList = productionDetailRepository.findProductionDetailByUserId(findExistingUser.getId());
        List<Order> orderList = orderRepository.findOrderByUserId(findExistingUser.getId());
        Salary salary = new Salary();
        // Tính toán phần (productionDetailList.size() - minKpi)
        double totalSalaryWithDoNotHaveKpi = (salaryDTO.getSalaryPerDate() * salaryDTO.getWorkingDate());
        if(findExistingUser.getRole().getName().equals("worker")){
            int productionMinusMinKpi = productionDetailList.size() - salaryDTO.getMinKpi();
            if (productionMinusMinKpi <= 0) {
                productionMinusMinKpi = 0;
            }

            // Tính toán tổng lương
            double totalSalary = (salaryDTO.getSalaryPerDate() * salaryDTO.getWorkingDate())
                    + (salaryDTO.getSalaryPerDate() * 26 * productionMinusMinKpi / 100.0);

            // Tính toán rateSa
            float rateSa = (float) (productionMinusMinKpi / 100.0);
            salary.setRateSa(rateSa);
            salary.setTotal(totalSalary);
        }
        else if(findExistingUser.getRole().getName().equals("sale")){
            int productionMinusMinKpi = orderList.size() - salaryDTO.getMinKpi();
            if (productionMinusMinKpi <= 0) {
                productionMinusMinKpi = 0;
            }

            // Tính toán tổng lương
            double totalSalary = (salaryDTO.getSalaryPerDate() * salaryDTO.getWorkingDate())
                    + (salaryDTO.getSalaryPerDate() * 26 * productionMinusMinKpi / 100.0);

            // Tính toán rateSa
            float rateSa = (float) (productionMinusMinKpi / 100.0);
            salary.setRateSa(rateSa);
            salary.setTotal(totalSalary);
        }
        salary.setSalaryPerDate(salaryDTO.getSalaryPerDate());
        salary.setWorkingDate(salaryDTO.getWorkingDate());
        salary.setMinKpi(salaryDTO.getMinKpi());
        salary.setTotal(totalSalaryWithDoNotHaveKpi);
        salary.setUser(findExistingUser);

        salary = salaryRepository.save(salary);
        return SalaryResponse.fromSalary(salary);
    }


    @Override
    public SalaryResponse updateSalary(Long id, SalaryDTO salaryDTO) throws Exception {
        Salary findExistingSalary = salaryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find this salary!"));
        User user = userRepository.findById(salaryDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this user"));

        List<ProductionDetail> productionDetailList = productionDetailRepository.findProductionDetailByUserId(user.getId());

        // Tính toán phần (productionDetailList.size() - minKpi)
        int productionMinusMinKpi = productionDetailList.size() - salaryDTO.getMinKpi();
        if (productionMinusMinKpi <= 0) {
            productionMinusMinKpi = 0;
        }

        // Tính toán tổng lương
        double totalSalary = (salaryDTO.getSalaryPerDate() * salaryDTO.getWorkingDate())
                + (salaryDTO.getSalaryPerDate() * 26 * productionMinusMinKpi / 100.0);

        // Tính toán rateSa
        float rateSa = (float) (productionMinusMinKpi / 100.0);

        findExistingSalary.setUser(user);
        findExistingSalary.setSalaryPerDate(salaryDTO.getSalaryPerDate());
        findExistingSalary.setWorkingDate(salaryDTO.getWorkingDate());
        findExistingSalary.setMinKpi(salaryDTO.getMinKpi());
        findExistingSalary.setRateSa(rateSa); // Cập nhật rateSa
        findExistingSalary.setTotal(totalSalary);

        return SalaryResponse.fromSalary(salaryRepository.save(findExistingSalary));
    }

    @Override
    public byte[] setNewMonth() throws Exception {
        List<Salary> salaries = salaryRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Salaries");
            Row headerRow = sheet.createRow(0);

            // Tạo header cho file Excel
            String[] columns = {"ID", "User ID", "Salary Per Date", "Working Date", "Min KPI", "Rate SA", "Total", "CreatedAt", "UpdatedAt"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Đổ dữ liệu vào file Excel
            int rowNum = 1;
            for (Salary salary : salaries) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(salary.getId());
                row.createCell(1).setCellValue(salary.getUser().getId());
                row.createCell(2).setCellValue(salary.getSalaryPerDate());
                row.createCell(3).setCellValue(salary.getWorkingDate());
                row.createCell(4).setCellValue(salary.getMinKpi());
                row.createCell(5).setCellValue(salary.getRateSa());
                row.createCell(6).setCellValue(salary.getTotal());

                // Đặt thuộc tính workingDate về 0
                salary.setWorkingDate(0);
                salary.setTotal(0);
                salaryRepository.save(salary);

                // Chuyển đổi giá trị LocalDateTime sang chuỗi ngày giờ phút giây
                Instant createdAtInstant = salary.getCreatedAt().toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
                LocalDateTime createdAt = LocalDateTime.ofInstant(createdAtInstant, ZoneId.systemDefault());

                Instant updatedAtInstant = salary.getUpdatedAt().toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
                LocalDateTime updatedAt = LocalDateTime.ofInstant(updatedAtInstant, ZoneId.systemDefault());

                row.createCell(7).setCellValue(createdAt.toString()); // Created At
                row.createCell(8).setCellValue(updatedAt.toString()); // Updated At
            }

            // Ghi workbook vào ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

//    @Override
//    public void timekeepingAll() throws Exception {
//        List<Salary> findAllSalary = salaryRepository.findAll();
//        for (Salary salary : findAllSalary){
//            salary.setWorkingDate(salary.getWorkingDate() + 1);
//        }
//    }

    @Override
    public void timekeepingAll() throws Exception {
        List<Salary> findAllSalary = salaryRepository.findAll();
        for (Salary salary : findAllSalary) {
            // Tăng số ngày làm việc lên 1
            salary.setWorkingDate(salary.getWorkingDate() + 1);

            // Tính lại tổng lương
            double totalSalary;
            if (salary.getUser().getRole().getName().equals("worker")) {
                List<ProductionDetail> productionDetailList = productionDetailRepository.findProductionDetailByUserId(salary.getUser().getId());
                int productionMinusMinKpi = productionDetailList.size() - salary.getMinKpi();
                if (productionMinusMinKpi <= 0) {
                    productionMinusMinKpi = 0;
                }
                totalSalary = (salary.getSalaryPerDate() * salary.getWorkingDate())
                        + (salary.getSalaryPerDate() * 26 * productionMinusMinKpi / 100.0);
            } else if (salary.getUser().getRole().getName().equals("sale")) {
                List<Order> orderList = orderRepository.findOrderByUserId(salary.getUser().getId());
                int orderMinusMinKpi = orderList.size() - salary.getMinKpi();
                if (orderMinusMinKpi <= 0) {
                    orderMinusMinKpi = 0;
                }
                totalSalary = (salary.getSalaryPerDate() * salary.getWorkingDate())
                        + (salary.getSalaryPerDate() * 26 * orderMinusMinKpi / 100.0);
            } else {
                // Handle other roles if needed
                totalSalary = (salary.getSalaryPerDate() * salary.getWorkingDate());
            }

            salary.setTotal(totalSalary);

            // Lưu lại thay đổi
            salaryRepository.save(salary);
        }
    }

    @Override
    public void deleteSalary(Long id) throws Exception {
        Salary findExistingSalary = salaryRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Cannot find this salary!"));
        salaryRepository.delete(findExistingSalary);
    }
}
