package com.project.SWP391.repositories;

import com.project.SWP391.entities.TimeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeCateRepository extends JpaRepository<TimeCategory,Long> {
    TimeCategory findByName(String name);
}
