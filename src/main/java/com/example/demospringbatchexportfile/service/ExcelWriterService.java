package com.example.demospringbatchexportfile.service;

import com.example.demospringbatchexportfile.model.HouseHold;
import com.example.demospringbatchexportfile.repository.HouseHoldRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelWriterService {
    private final HouseHoldRepository houseHoldRepository;

    public File writeData() {
        List<HouseHold> houseHolds = houseHoldRepository.findAll();
        File file = new File("D:\\java\\spring-batch-export-data\\src\\main\\resources\\data.xlsx");
        SXSSFWorkbook sxssfWorkbook = null;
        try (SXSSFWorkbook workbook = sxssfWorkbook = new SXSSFWorkbook(1);
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            Sheet sheet = workbook.createSheet();
            CellStyle cellStyle = workbook.createCellStyle();
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("EMAIL");
            header.createCell(2).setCellValue("USERNAME");
            header.createCell(3).setCellValue("ADDRESS-DETAIL");
            header.createCell(4).setCellValue("WARD");
            header.createCell(5).setCellValue("DISTRICT");
            header.createCell(6).setCellValue("PROVINCE");
            header.createCell(7).setCellValue("QUANTITY");
            header.createCell(8).setCellValue("AMOUNT");
            header.createCell(9).setCellValue("PRODUCT-NAME");
            int rowNum = 1;
            for (HouseHold houseHold : houseHolds) {
                Row row = sheet.createRow(rowNum);
                addDataInRow(houseHold, row, cellStyle);
                rowNum++;
            }

            //workbook.write causing CPU spike
            workbook.write(fileOutputStream);

            workbook.dispose();
        } catch (Exception exception) {
            return null;
        } finally {
            if (sxssfWorkbook != null) {
                sxssfWorkbook.dispose();
            }
        }

        return file;
    }

    private void addDataInRow(HouseHold houseHold, Row row, CellStyle cellStyle) {
        Cell cell_0 = row.createCell(0);
        cell_0.setCellValue(houseHold.getId());
        cell_0.setCellStyle(cellStyle);

        Cell cell_1 = row.createCell(1);
        cell_1.setCellValue(houseHold.getEmail());
        cell_1.setCellStyle(cellStyle);

        Cell cell_2 = row.createCell(2);
        cell_2.setCellValue(houseHold.getUsername());
        cell_2.setCellStyle(cellStyle);

        Cell cell_3 = row.createCell(3);
        cell_3.setCellValue(houseHold.getAddressDetail());
        cell_3.setCellStyle(cellStyle);

        Cell cell_4 = row.createCell(4);
        cell_4.setCellValue(houseHold.getWard());
        cell_4.setCellStyle(cellStyle);

        Cell cell_5 = row.createCell(5);
        cell_5.setCellValue(houseHold.getDistrict());
        cell_5.setCellStyle(cellStyle);

        Cell cell_6 = row.createCell(6);
        cell_6.setCellValue(houseHold.getProvince());
        cell_6.setCellStyle(cellStyle);

        Cell cell_7 = row.createCell(7);
        cell_7.setCellValue(houseHold.getProductName());
        cell_7.setCellStyle(cellStyle);

        Cell cell_8 = row.createCell(8);
        cell_8.setCellValue(houseHold.getQuantity());
        cell_8.setCellStyle(cellStyle);

        Cell cell_9 = row.createCell(9);
        cell_9.setCellValue(houseHold.getAmount());
        cell_9.setCellStyle(cellStyle);
    }
}
