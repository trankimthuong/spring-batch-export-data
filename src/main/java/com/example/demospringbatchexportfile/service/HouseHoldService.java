package com.example.demospringbatchexportfile.service;

import com.example.demospringbatchexportfile.model.HouseHold;
import com.example.demospringbatchexportfile.repository.HouseHoldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseHoldService {
    private final HouseHoldRepository houseHoldRepository;

    public HouseHold create() {
        HouseHold houseHold = HouseHold.builder()
                .username("thuongtk")
                .email("thuongtk.bk@gmail.com")
                .addressDetail("Số 45 ngõ 153 Phú Đô")
                .ward("Thanh Mỹ")
                .district("Thanh Chương")
                .province("Nghệ An")
                .productName("MILO")
                .amount(25000)
                .quantity(1)
                .build();
        return houseHoldRepository.save(houseHold);
    }
}
