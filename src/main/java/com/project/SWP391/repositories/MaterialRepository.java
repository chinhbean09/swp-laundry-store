package com.project.SWP391.repositories;

import com.project.SWP391.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {

   Material findByName(String name);


}
