package com.backendserver.DigitronixProject.services.DataAccess;

import com.backendserver.DigitronixProject.dtos.DataAccessDTO;
import com.backendserver.DigitronixProject.dtos.MaterialDataAccessDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.DataAccess;
import com.backendserver.DigitronixProject.models.MaterialDataAccess;
import com.backendserver.DigitronixProject.repositories.DataAccessRepository;
import com.backendserver.DigitronixProject.repositories.MaterialDataAccessRepository;
import com.backendserver.DigitronixProject.responses.MaterialDataAccessResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DataAccessService implements IDataAccessService {
    private final DataAccessRepository dataAccessRepository;
    private final MaterialDataAccessRepository materialDataAccessRepository;
    @Override
    public DataAccess createDataAccess(DataAccessDTO dataAccessDTO) {
        DataAccess newData = DataAccess.builder()
                .productId(dataAccessDTO.getProductId())
                .productName(dataAccessDTO.getProductName())
                .productQuantity(dataAccessDTO.getProductQuantity())
                .status(dataAccessDTO.isStatus())
                .build();
        return dataAccessRepository.save(newData);
    }

    @Override
    public DataAccess getById(Long id) {
        return dataAccessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found"));
    }

    @Override
    public List<DataAccess> getAll() {
        return dataAccessRepository.findAll();
    }

    @Override
    public byte[] exportDataAsExcel() throws IOException {
        List<DataAccess> data = getAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");
            Row headerRow = sheet.createRow(0);

            // Tạo header cho file Excel
            String[] columns = {"ID", "Product ID", "Product Name", "Product Quantity", "Status", "CreatedAt", "UpdatedAt"}; // Tên cột tương ứng với các trường của DataAccess
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Đổ dữ liệu vào file Excel
            int rowNum = 1;
            for (DataAccess item : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getProductId());
                row.createCell(2).setCellValue(item.getProductName());
                row.createCell(3).setCellValue(item.getProductQuantity());
                row.createCell(4).setCellValue(item.isStatus());

                // Chuyển đổi giá trị LocalDateTime sang chuỗi ngày giờ phút giây
                Instant createdAtInstant = item.getCreatedAt().toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
                LocalDateTime createdAt = LocalDateTime.ofInstant(createdAtInstant, ZoneId.systemDefault());

                Instant updatedAtInstant = item.getUpdatedAt().toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
                LocalDateTime updatedAt = LocalDateTime.ofInstant(updatedAtInstant, ZoneId.systemDefault());

                row.createCell(5).setCellValue(createdAt.toString()); // Created At
                row.createCell(6).setCellValue(updatedAt.toString()); // Updated At
            }
            // Ghi workbook vào ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    @Override
    public MaterialDataAccessResponse createMaterialDataAccess(MaterialDataAccessDTO materialDataAccessDTO) throws Exception {
        MaterialDataAccess materialDataAccess = new MaterialDataAccess();
        materialDataAccess.setMaterialId(materialDataAccessDTO.getMaterialId());
        materialDataAccess.setMaterialName(materialDataAccessDTO.getMaterialName());
        materialDataAccess.setMaterialQuantity(materialDataAccessDTO.getMaterialQuantity());
        materialDataAccess.setStatus(materialDataAccessDTO.isStatus());

        return MaterialDataAccessResponse.fromMaterialDataAccess(materialDataAccessRepository.save(materialDataAccess));
    }

    @Override
    public List<MaterialDataAccessResponse> getAllMaterialDataAccess() {
        List<MaterialDataAccess> materialDataAccesses = materialDataAccessRepository.findAll();

        return materialDataAccesses.stream().map(MaterialDataAccessResponse::fromMaterialDataAccess).toList();
    }

    @Override
    public MaterialDataAccessResponse getMaterialDataAccessById(Long id) throws Exception {
        MaterialDataAccess materialDataAccess = materialDataAccessRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Cannot find this material access in system!"));
        return MaterialDataAccessResponse.fromMaterialDataAccess(materialDataAccess);
    }

    @Override
    public void deleteAllMaterialDataAccess() {
        materialDataAccessRepository.deleteAll();
    }

    @Override
    public byte[] exportMaterialDataAccessAsExcel() throws IOException {
        List<MaterialDataAccessResponse> materialData = getAllMaterialDataAccess();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("MaterialData");
            Row headerRow = sheet.createRow(0);

            // Tạo header cho file Excel
            String[] columns = {"ID", "Material ID", "Material Name", "Material Quantity", "Status", "CreatedAt", "UpdatedAt"}; // Tên cột tương ứng với các trường của DataAccess
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Đổ dữ liệu vào file Excel
            int rowNum = 1;
            for (MaterialDataAccessResponse item : materialData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getMaterialId());
                row.createCell(2).setCellValue(item.getMaterialName());
                row.createCell(3).setCellValue(item.getMaterialQuantity());
                row.createCell(4).setCellValue(item.isStatus());

                // Chuyển đổi giá trị LocalDateTime sang chuỗi ngày giờ phút giây
                Instant createdAtInstant = item.getCreatedAt().toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
                LocalDateTime createdAt = LocalDateTime.ofInstant(createdAtInstant, ZoneId.systemDefault());

                Instant updatedAtInstant = item.getUpdatedAt().toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
                LocalDateTime updatedAt = LocalDateTime.ofInstant(updatedAtInstant, ZoneId.systemDefault());

                row.createCell(5).setCellValue(createdAt.toString()); // Created At
                row.createCell(6).setCellValue(updatedAt.toString()); // Updated At
            }
            // Ghi workbook vào ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }


    @Override
    public String deleteAll(){
        dataAccessRepository.deleteAll();
        return "All data have been deleted!";
    }
}
