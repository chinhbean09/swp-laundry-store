package com.project.SWP391.services;

import com.project.SWP391.requests.StoreTimeRequest;
import com.project.SWP391.responses.dto.StoreTimeDTO;

import java.util.List;

public interface StoreTimeService {
    StoreTimeDTO updateStoreTime (Long id, Float request, String dateRange);

    public void setStatusOfStoreTime (Long id, int status);
    List<StoreTimeDTO> getAllStoreTime();

    List<StoreTimeDTO> getAllStoreTimeForCustomer(Long storeId);

    StoreTimeDTO createStoreTime (StoreTimeRequest request);
}
