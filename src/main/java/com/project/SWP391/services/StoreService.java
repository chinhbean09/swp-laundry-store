package com.project.SWP391.services;

import com.project.SWP391.requests.SpecialServiceFilterRequest;
import com.project.SWP391.requests.StoreRegisterRequest;
import com.project.SWP391.responses.dto.StoreInfoDTO;

import java.util.List;

public interface StoreService {
    StoreInfoDTO createStore(StoreRegisterRequest request);
    StoreInfoDTO updateStore(StoreInfoDTO request);

    StoreInfoDTO getCurrentStore();

    StoreInfoDTO getStoreById(Long id);

    List<StoreInfoDTO> getAllStore();
    void deleteStore(Long id);

    List<StoreInfoDTO> getAllStoreByFilter(SpecialServiceFilterRequest request);
}
