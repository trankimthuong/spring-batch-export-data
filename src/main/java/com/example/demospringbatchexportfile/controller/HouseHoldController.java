package com.example.demospringbatchexportfile.controller;

import com.example.demospringbatchexportfile.model.HouseHold;
import com.example.demospringbatchexportfile.service.ExcelWriterService;
import com.example.demospringbatchexportfile.service.HouseHoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("/house-holds")
public class HouseHoldController {
    private final HouseHoldService houseHoldService;
    private final ExcelWriterService excelWriterService;

    @GetMapping
    public HouseHold createDummyData() {
        return houseHoldService.create();
    }

    @GetMapping("/export-data")
    public File exportFile() {
        return excelWriterService.writeData();
    }
}
