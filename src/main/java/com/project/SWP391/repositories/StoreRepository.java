package com.project.SWP391.repositories;

import com.project.SWP391.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findStoreByUserId(Long id);
}
