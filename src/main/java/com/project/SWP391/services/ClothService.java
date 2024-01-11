package com.project.SWP391.services;

import com.project.SWP391.responses.dto.ClothDTO;

import java.util.List;

public interface ClothService {
    ClothDTO createNewCloth (ClothDTO request);
    ClothDTO updateCloth(Long id, String name);

    List<ClothDTO> getAllCloth ();

    void deleteCloth(Long id);
}