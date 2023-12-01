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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelWriterService {
    private final HouseHoldRepository houseHoldRepository;

    private final static Integer MAX_DATA_SIZE_HANDLE = 50000;

    public ByteArrayInputStream writeData() throws InterruptedException, IOException {
        int count = (int) houseHoldRepository.count();
        int pageSize = count % 10 == 0 ? count / MAX_DATA_SIZE_HANDLE : count / MAX_DATA_SIZE_HANDLE + 1;
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        Sheet sheet = sxssfWorkbook.createSheet();
        for (int i = 0; i < pageSize; i++) {
            retrieveData(sheet, i);
        }
        ByteArrayOutputStream outP = new ByteArrayOutputStream();
        try {
            sxssfWorkbook.write(outP);
            return new ByteArrayInputStream(outP.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (sxssfWorkbook != null) {
                sxssfWorkbook.dispose();
                sxssfWorkbook.close();
            }
        }
    }

    private void retrieveData(Sheet sheet, int currentPage) {
        int offset = currentPage * MAX_DATA_SIZE_HANDLE;
        int currentRow = 0;

        if (currentPage == 0) {
            createHeader(sheet);
            currentRow = 1;
        } else {
            currentRow = currentPage * MAX_DATA_SIZE_HANDLE + 1;
        }
        List<HouseHold> houseHolds = houseHoldRepository.getListOrderById(MAX_DATA_SIZE_HANDLE, offset);
        for (HouseHold houseHold : houseHolds) {
            Row row = sheet.createRow(currentRow);
            addDataInRow(houseHold, row, null);
            currentRow++;
        }
    }

    private void createHeader(Sheet sheet) {
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
