package com.example.demospringbatchexportfile.repository;

import com.example.demospringbatchexportfile.model.HouseHold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseHoldRepository extends JpaRepository<HouseHold, Integer> {
}
