package com.project.SWP391.services.ServiceImp;

import com.project.SWP391.entities.Cloth;
import com.project.SWP391.entities.Material;
import com.project.SWP391.repositories.MaterialRepository;
import com.project.SWP391.responses.dto.ClothDTO;
import com.project.SWP391.responses.dto.MaterialDTO;
import com.project.SWP391.services.MaterialService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialServiceImp implements MaterialService {

    private final MaterialRepository materialRepository;

    private final ModelMapper mapper;

    @Override
    public MaterialDTO createNewMaterial(MaterialDTO request) {
        return null;
    }

    @Override
    public MaterialDTO updateMaterial(String name, Long id) {
        return null;
    }



    @Override
    public List<MaterialDTO> getAllMaterials() {
        var list = materialRepository.findAll();
        Predicate<Material> byDelete = material ->  material.getStatus() == 1 ;
        return list.stream().filter(byDelete).map(material -> mapToDTO(material)).collect(Collectors.toList());
    }

    @Override
    public Long deleteMaterial(Long id) {
        return null;
    }

    private MaterialDTO mapToDTO(Material dto) {
        return mapper.map(dto, MaterialDTO.class);
    }
}
