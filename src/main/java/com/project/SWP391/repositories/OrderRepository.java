package com.project.SWP391.repositories;

import com.project.SWP391.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByStoreId(Long id);
    List<Order> findAllByUserId(Long id);
}
