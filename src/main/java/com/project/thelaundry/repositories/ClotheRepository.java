package com.project.thelaundry.repositories;

import com.project.thelaundry.entities.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClotheRepository extends JpaRepository<Cloth, Long> {
    Cloth findByName(String name);
}
