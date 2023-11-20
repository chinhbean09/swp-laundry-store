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
    private final LaundryServiceImp service;
    //    public StoreController(LaundryServiceImp laundryservice) {
//        this.laundryservice = laundryservice;
//    }
    @GetMapping("/special-service/all")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<LaundryInfoDTO>> getAllSpecialServices(@RequestParam(name="store")  long id){
        return ResponseEntity.ok(service.getAllSpecialServiceForStore(id));
    }

    @GetMapping("/standard-service/get")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<LaundryInfoDTO> getStandardServicesById(@RequestParam(name = "store") long id) {
        return  ResponseEntity.ok(service.getStandardServiceForStore(id));
    }

    @PutMapping("/special-service/update/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<LaundryInfoDTO> updateSpecialService(@RequestBody SpecialServiceRequest request,@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.updateSpecialService(request,id));
    }

    @DeleteMapping("/special-service/delete/{id}")
    @PreAuthorize("hasAuthority('store:delete')")
    public ResponseEntity deleteSpecialService(@PathVariable(name = "id") long id) {
        service.deleteService(id);
        return  ResponseEntity.ok().build();
    }

    @PutMapping("/standard-service/update/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<LaundryInfoDTO> updateStandardService(@RequestBody StandardServiceRequest request,@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.updateStandardService(request,id));
    }

    @DeleteMapping("/standard-service/delete/{id}")
    @PreAuthorize("hasAuthority('store:delete')")
    public ResponseEntity deleteStandardService(@PathVariable(name = "id") long id) {
        service.deleteService(id);
        return ResponseEntity.ok().build();

    }


    @DeleteMapping("/standard-service/prices/delete/{id}")
    @PreAuthorize("hasAuthority('store:delete')")
    public ResponseEntity deletePricesStandardService(@PathVariable(name = "id") long id) {
        service.deletePrice(id);
        return ResponseEntity.ok().build();

    }
}

