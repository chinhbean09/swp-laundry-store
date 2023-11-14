package com.project.thelaundry.repositories;

import com.project.SWP391.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findStoreByUserId(Long id);

}
