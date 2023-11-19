package com.example.demospringbatchexportfile.controller;

import com.example.demospringbatchexportfile.model.HouseHold;
import com.example.demospringbatchexportfile.service.HouseHoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/house-holds")
public class HouseHoldController {
    private final HouseHoldService houseHoldService;

    @GetMapping
    public HouseHold createDummyData() {
        return houseHoldService.create();
    }
}
