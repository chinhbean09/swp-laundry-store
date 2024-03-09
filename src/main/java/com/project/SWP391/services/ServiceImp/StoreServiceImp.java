package com.project.SWP391.services.ServiceImp;


import com.project.SWP391.entities.Laundry;
import com.project.SWP391.entities.Store;
import com.project.SWP391.repositories.LaundryServiceRepository;
import com.project.SWP391.repositories.StoreRepository;
import com.project.SWP391.repositories.UserRepository;
import com.project.SWP391.requests.SpecialServiceFilterRequest;
import com.project.SWP391.requests.StoreRegisterRequest;
import com.project.SWP391.responses.dto.StoreInfoDTO;
import com.project.SWP391.security.utils.SecurityUtils;
import com.project.SWP391.services.StoreService;
import com.project.SWP391.specifications.CustomServiceSpec;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImp implements StoreService {

    private final LaundryServiceRepository serviceRepository;
    private final StoreRepository storeRepository;
    private final UserRepository  userRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public StoreInfoDTO createStore(StoreRegisterRequest request) {
        var user = userRepository.findById(SecurityUtils.getPrincipal().getId()).orElseThrow();
        var store = Store.builder()
                .address(request.getAddress())
                .status(1)
                .district(request.getDistrict())
                .phone(request.getPhone())
                .name(request.getName())
                .user(user).build();
        storeRepository.save(store);
        return mapToDTO(store);
    }

    @Override
    public StoreInfoDTO updateStore(StoreInfoDTO request) {
        var user = userRepository.findById(SecurityUtils.getPrincipal().getId()).orElseThrow();
        var store = storeRepository.findStoreByUserId(user.getId());
        store.setAddress(request.getAddress());
        store.setDistrict(request.getDistrict());
        store.setName(request.getName());
        store.setPhone(request.getPhone());
        var newStore = storeRepository.save(store);
        return mapToDTO(newStore);
    }

    @Override
    public StoreInfoDTO getCurrentStore() {
       var user = userRepository.findById(SecurityUtils.getPrincipal().getId()).orElseThrow();
       var store = storeRepository.findStoreByUserId(user.getId());
       return mapToDTO(store);

    }

    @Override
    public StoreInfoDTO getStoreById(Long id) {
        var store = storeRepository.findById(id).orElseThrow();
        return mapToDTO(store);
    }

    @Override
    public List<StoreInfoDTO> getAllStore() {
        var list = storeRepository.findAll();
        Predicate<Store> byDelete = store -> store.getStatus() == 1;

        return list.stream().filter(byDelete).map(laundry -> mapToDTO(laundry)).collect(Collectors.toList());

    }

    @Override
    public void deleteStore(Long id) {

        var store = storeRepository.findById(id).orElseThrow();
        store.setStatus(0);
        storeRepository.save(store);
    }

    @Override
    public List<StoreInfoDTO> getAllStoreByFilter(SpecialServiceFilterRequest request) {

        List<Store> stores = new ArrayList<>();
        if((request.getMaterials() != null && !request.getMaterials().isEmpty()) || request.getClothId() != null){
            CustomServiceSpec spec = new CustomServiceSpec(request);
            var list = serviceRepository.findAll(spec);
            List<Long> ids = new ArrayList<>();
            for (Laundry item : list
            ) {

                ids.add(item.getId() );

            }

            stores = storeRepository.findAllById(serviceRepository.findAllStoreByFilter(ids));

        }else{
            stores = storeRepository.findAll();
        }


        if(request.getDistrict() != null && !request.getDistrict().isBlank()){
            Predicate<Store> byDistrict = store -> store.getDistrict().equals(request.getDistrict());
            return stores.stream().filter(byDistrict).map(store -> mapToDTO(store)).collect(Collectors.toList());
        }
        return stores.stream().map(store -> mapToDTO(store)).collect(Collectors.toList());

    }

    private StoreInfoDTO mapToDTO(Store dto) {
        return mapper.map(dto, StoreInfoDTO.class);
    }
}
