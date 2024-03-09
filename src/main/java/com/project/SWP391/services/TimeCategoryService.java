package com.project.SWP391.services;

import com.project.SWP391.requests.TimeCategoryRequest;
import com.project.SWP391.responses.dto.TimeDTO;

import java.util.List;

public interface TimeCategoryService {
    TimeDTO createCategory(TimeCategoryRequest request);
    public void deleteCategory(Long id);

    List<TimeDTO> getAll();
}
