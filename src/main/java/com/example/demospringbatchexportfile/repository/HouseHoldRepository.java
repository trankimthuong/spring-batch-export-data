package com.example.demospringbatchexportfile.repository;

import com.example.demospringbatchexportfile.model.HouseHold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseHoldRepository extends JpaRepository<HouseHold, Integer> {
    @Query(value = "select * from house_holds order by id limit :limit offset :offset", nativeQuery = true)
    List<HouseHold> getListOrderById(int limit, int offset);

}
