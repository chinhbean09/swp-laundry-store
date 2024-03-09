package com.project.SWP391.services.ServiceImp;

import com.project.SWP391.entities.Material;
import com.project.SWP391.repositories.MaterialRepository;
import com.project.SWP391.responses.dto.MaterialDTO;
import com.project.SWP391.services.MaterialService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
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
        var material = materialRepository.findByName(request.getName());
        if(material != null){

            material.setStatus(1);
            materialRepository.save(material);

            return mapToDTO(material);
        }
        var newMaterial = Material.builder().status(1)
                .name(request.getName()).build();
        materialRepository.save(newMaterial);
        return  mapToDTO(newMaterial);

    }

    @Override
    public MaterialDTO updateMaterial(String name, Long id) {
        var material = materialRepository.findByName(name);
        if(material == null){
            var updateMaterial = materialRepository.findById(id).orElseThrow();
            updateMaterial.setName(name);
            materialRepository.save(updateMaterial);

            return mapToDTO(updateMaterial);
        }
        throw new DuplicateFormatFlagsException("Name of material is existed");
    }



    @Override
    public List<MaterialDTO> getAllMaterials() {
        var list = materialRepository.findAll();
        Predicate<Material> byDelete = material ->  material.getStatus() == 1 ;
        return list.stream().filter(byDelete).map(material -> mapToDTO(material)).collect(Collectors.toList());
    }

    @Override
    public void deleteMaterial(Long id) {
        var updateMaterial = materialRepository.findById(id).orElseThrow();
        updateMaterial.setStatus(0);
        materialRepository.save(updateMaterial);

    }

    private MaterialDTO mapToDTO(Material dto) {
        return mapper.map(dto, MaterialDTO.class);
    }
}
