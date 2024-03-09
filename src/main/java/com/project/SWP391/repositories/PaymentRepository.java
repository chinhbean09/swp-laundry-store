package com.project.SWP391.repositories;

import com.project.SWP391.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment findByOrderId(Long id);
}
