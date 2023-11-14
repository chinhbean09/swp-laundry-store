package com.project.thelaundry.repositories;

import com.project.thelaundry.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByStoreId(Long id);
    List<Order> findAllByUserId(Long id);

    Optional<Order> findById(Long id);
}
