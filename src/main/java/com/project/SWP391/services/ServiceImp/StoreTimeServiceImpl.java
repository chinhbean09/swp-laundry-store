package com.project.SWP391.services.ServiceImp;

import com.project.SWP391.entities.Time;
import com.project.SWP391.repositories.StoreRepository;
import com.project.SWP391.repositories.StoreTimeRepository;
import com.project.SWP391.repositories.TimeCateRepository;
import com.project.SWP391.requests.StoreTimeRequest;
import com.project.SWP391.responses.dto.StoreTimeDTO;
import com.project.SWP391.security.utils.SecurityUtils;
import com.project.SWP391.services.StoreTimeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class StoreTimeServiceImpl implements StoreTimeService {

    private final StoreTimeRepository repository;

    private final StoreRepository storeRepository;

    private final TimeCateRepository timeCateRepository;

    @Autowired
    private final ModelMapper mapper;

    @Override
    public StoreTimeDTO updateStoreTime(Long id, Float request, String dateRange) {

        var time = repository.findById(id).orElseThrow();
        time.setPrice(request);
        time.setDateRange(dateRange);
        repository.save(time);

        return mapToDTO(time);
    }

    @Override
    public void setStatusOfStoreTime (Long id, int status) {
        var time = repository.findById(id).orElseThrow();
        time.setStatus(status);
        repository.save(time);
    }

    @Override
    public List<StoreTimeDTO> getAllStoreTime() {
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());

        List<Time> times = repository.findAllByStoreId(store.getId());
        return times.stream().map(time -> mapToDTO(time)).collect(Collectors.toList());
    }

    @Override
    public List<StoreTimeDTO> getAllStoreTimeForCustomer(Long storeId) {
        List<Time> times = repository.findAllByStoreId(storeId);
        Predicate<Time> byStatus = time -> time.getStatus() == 1;
        return times.stream().filter(byStatus).map(time -> mapToDTO(time)).collect(Collectors.toList());
    }

    @Override
    public StoreTimeDTO createStoreTime(StoreTimeRequest request) {
        var store =  storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        var existed = repository.findWithStoreAndTimeCate(request.getTimeCategoryId(), store.getId());

        var cate =  timeCateRepository.findById(request.getTimeCategoryId()).orElseThrow();
        if (existed != null){
            existed.setDateRange(request.getDateRange());
            existed.setPrice(request.getPrice());
            existed.setStatus(1);
            var edited= repository.save(existed);
            return  mapToDTO(edited);
        }
        var time = Time.builder().dateRange(request.getDateRange()).
                price(request.getPrice())
                .store(store)
                .timeCategory(cate)
                .status(1).build();
        var newData = repository.save(time);
        return  mapToDTO(newData);
    }

    private StoreTimeDTO mapToDTO(Time dto) {
        return mapper.map(dto, StoreTimeDTO.class);
    }
}
