package com.example.demospringbatchexportfile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "house_holds")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseHold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String addressDetail;
    private String ward;
    private String district;
    private String province;
    private Integer quantity;
    private Integer amount;
    private String productName;
}
