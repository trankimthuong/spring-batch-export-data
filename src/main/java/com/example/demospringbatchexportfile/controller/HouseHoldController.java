package com.example.demospringbatchexportfile.controller;

import com.example.demospringbatchexportfile.model.HouseHold;
import com.example.demospringbatchexportfile.service.ExcelWriterService;
import com.example.demospringbatchexportfile.service.HouseHoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.concurrent.Executor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/house-holds")
public class HouseHoldController {
    private final HouseHoldService houseHoldService;
    private final ExcelWriterService excelWriterService;
    private final Executor executor;

    @GetMapping
    public HouseHold createDummyData() {
        return houseHoldService.create();
    }

    @GetMapping(value = "/export-data")
    public ResponseEntity<ResponseBodyEmitter> exportFile() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        executor.execute(() -> {
            try {
                emitter.send(
                        new InputStreamResource(excelWriterService.writeData())
                );
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "hehe.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(emitter);

//        return excelWriterService.writeData();
    }

//    @GetMapping(value = "/export-data")
//    public ByteArrayInputStream exportFile() throws InterruptedException, IOException {
//        return excelWriterService.writeData();
//    }
}
