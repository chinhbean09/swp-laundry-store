package com.project.SWP391.services;

import com.project.SWP391.responses.dto.MaterialDTO;

import java.util.List;

public interface MaterialService {
    MaterialDTO createNewMaterial(MaterialDTO request);
    MaterialDTO updateMaterial(String name, Long id);

    List<MaterialDTO> getAllMaterials();

    void deleteMaterial(Long id);
}
