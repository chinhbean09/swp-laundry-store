package com.project.SWP391.repositories;

import com.project.SWP391.entities.Laundry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface LaundryServiceRepository extends JpaRepository<Laundry, Long>, JpaSpecificationExecutor<Laundry> {
    List<Laundry> findAllByStoreId(Long id);

    Laundry findByStoreIdAndIsStandardTrue(Long id);

    List<Laundry> findAllByClothId(Long id);

    @Query(value = "SELECT DISTINCT store.id "
            + "FROM Laundry "
            + "WHERE id IN :ids ")
    List<Long> findAllStoreByFilter(@Param("ids") List<Long> specialIds);



}
