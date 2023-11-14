package com.project.thelaundry.repositories;

import com.project.thelaundry.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findStoreByUserId(Long id);

}
