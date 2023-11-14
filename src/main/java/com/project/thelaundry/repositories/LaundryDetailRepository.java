package com.project.thelaundry.repositories;


import com.project.thelaundry.entities.LaundryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaundryDetailRepository extends JpaRepository<LaundryDetail, Long> {
    LaundryDetail findByLaundryServiceId(Long id);
    List<LaundryDetail> findAllByLaundryServiceId(Long id);
}
