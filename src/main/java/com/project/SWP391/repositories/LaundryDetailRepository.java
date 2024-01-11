package com.project.SWP391.repositories;


import com.project.SWP391.entities.LaundryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaundryDetailRepository extends JpaRepository<LaundryDetail, Long> {
    LaundryDetail findByLaundryServiceId(Long id);
    List<LaundryDetail> findAllByLaundryServiceId(Long id);
}
