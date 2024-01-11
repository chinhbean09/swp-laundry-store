package com.project.SWP391.repositories;

import com.project.SWP391.entities.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClotheRepository extends JpaRepository<Cloth, Long> {
    Cloth findByName(String name);
}
