package com.project.SWP391.repositories;

import com.project.SWP391.entities.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreTimeRepository extends JpaRepository<Time, Long> {
    @Query(value = "SELECT t FROM Time t WHERE t.timeCategory.id = :id AND t.store.id = :storeId")
    Time findWithStoreAndTimeCate(@Param("id") Long id, @Param("storeId") Long storeId);


    List<Time> findAllByStoreId(Long id);
}
