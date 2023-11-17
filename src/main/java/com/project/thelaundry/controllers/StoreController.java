package com.project.thelaundry.controllers;
import com.project.thelaundry.requests.SpecialServiceRequest;
import com.project.thelaundry.requests.StandardServiceRequest;

import com.project.thelaundry.responses.dto.LaundryInfoDTO;
import com.project.thelaundry.services.LaundryServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.project.thelaundry.responses.dto.*;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/store")

@PreAuthorize("hasRole('STORE')")
@RequiredArgsConstructor

public class StoreController {

}

