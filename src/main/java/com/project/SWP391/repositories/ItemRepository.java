package com.project.SWP391.repositories;

import com.project.SWP391.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByOrderId (Long id);
}
