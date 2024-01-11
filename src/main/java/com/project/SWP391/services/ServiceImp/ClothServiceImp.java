package com.project.SWP391.services.ServiceImp;

import com.project.SWP391.entities.Cloth;


import com.project.SWP391.repositories.ClotheRepository;
import com.project.SWP391.responses.dto.ClothDTO;

import com.project.SWP391.services.ClothService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClothServiceImp implements ClothService {
    private final ClotheRepository clotherepository;
    @Autowired
    private final ModelMapper mapper;

    @Override
    public ClothDTO createNewCloth(ClothDTO request) {
        var existedCloth = clotherepository.findByName((request.getName()).toLowerCase(Locale.ROOT));

        if (existedCloth != null){
           existedCloth.setName(request.getName());
           var newCloth = clotherepository.save(existedCloth);
           return  mapToDTO(newCloth);
        }
        var cloth = Cloth.builder().name(request.getName())
                .status(1).build();
        var newCloth = clotherepository.save(cloth);
        return  mapToDTO(newCloth);


    }

    @Override
    public ClothDTO updateCloth(Long id, String name) {
        var existedCloth = clotherepository.findById(id).orElseThrow();
        if(existedCloth.getStatus() == 0 ){
            throw new RuntimeException("Value is not found !!!");
        }
        existedCloth.setName(name);
        var newCloth = clotherepository.save(existedCloth);
        return  mapToDTO(newCloth);
    }

    @Override
    public List<ClothDTO> getAllCloth() {
        Predicate<Cloth> byDeleted = cloth -> cloth.getStatus() == 0;
        List<Cloth> list = clotherepository.findAll();
        return  list.stream().filter(byDeleted).map(cloth -> mapToDTO(cloth)).collect(Collectors.toList());
    }

    @Override
    public void deleteCloth(Long id) {
        var existedCloth = clotherepository.findById((id)).orElseThrow();
        existedCloth.setStatus(0);
        var newCloth = clotherepository.save(existedCloth);
    }

    private ClothDTO mapToDTO(Cloth dto) {
        return mapper.map(dto, ClothDTO.class);
    }
}
