package com.project.SWP391.repositories;

import com.project.SWP391.entities.Material;
import com.project.SWP391.responses.dto.MaterialDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {




}
