package com.project.SWP391.services.ServiceImp;

import com.project.SWP391.entities.TimeCategory;
import com.project.SWP391.repositories.TimeCateRepository;
import com.project.SWP391.requests.TimeCategoryRequest;
import com.project.SWP391.responses.dto.TimeDTO;
import com.project.SWP391.services.TimeCategoryService;
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
public class TimeCategoryImp implements TimeCategoryService {
    private final TimeCateRepository timeCateRepository;

    @Autowired
    private final ModelMapper mapper;
    @Override
    public TimeDTO createCategory(TimeCategoryRequest request) {
        var existed = timeCateRepository.findByName((request.getName()).toLowerCase(Locale.ROOT));

        if (existed != null){
            existed.setName(request.getName());
            existed.setStatus(1);
            var newCloth = timeCateRepository.save(existed);
            return  mapToDTO(newCloth);
        }
        var time = TimeCategory.builder().name(request.getName())
                .status(1).build();
        var newCloth = timeCateRepository.save(time);
        return  mapToDTO(time);
    }

    @Override
    public void deleteCategory(Long id) {
        var existed = timeCateRepository.findById(id).orElseThrow();

        if (existed != null){

            existed.setStatus(0);
            var newCloth = timeCateRepository.save(existed);

        }

    }

    @Override
    public List<TimeDTO> getAll() {
        var list = timeCateRepository.findAll();
        Predicate<TimeCategory> byStatus = timeCategory -> timeCategory.getStatus() ==1;
        return list.stream().map(timeCategory -> mapToDTO(timeCategory)).collect(Collectors.toList());
    }

    private TimeDTO mapToDTO(TimeCategory dto) {
        return mapper.map(dto, TimeDTO.class);
    }
}
