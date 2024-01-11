package com.project.SWP391.controllers;

import com.project.SWP391.requests.SpecialServiceRequest;
import com.project.SWP391.requests.StandardServiceRequest;
import com.project.SWP391.requests.StoreRegisterRequest;
import com.project.SWP391.responses.dto.LaundryInfoDTO;


import com.project.SWP391.responses.dto.StoreInfoDTO;
import com.project.SWP391.responses.dto.UserInfoDTO;
import com.project.SWP391.services.LaundryServiceImp;


import com.project.SWP391.services.StoreService;
import com.project.SWP391.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
@PreAuthorize("hasRole('STORE')")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Store", description = "Store management APIs")
public class StoreController {
    private final LaundryServiceImp service;
    private final StoreService storeService;
    private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<UserInfoDTO> getProfile() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/profile/update/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<UserInfoDTO> updateProfile(@PathVariable Long id, @RequestBody UserInfoDTO request) {
        return ResponseEntity.ok(userService.updateUser(id,request));
    }

   @GetMapping("/special-service/all")
   @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<LaundryInfoDTO>> getAllSpecialServices() {
        return ResponseEntity.ok(service.getAllSpecialServiceForStore());
    }

    @GetMapping("/special-service/get/{id}")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<LaundryInfoDTO> getSpecialServicesById(@PathVariable(name = "id") long id) {
       return  ResponseEntity.ok(service.getServiceForStore(id));
    }

    @GetMapping("/standard-service/get")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<LaundryInfoDTO> getStandardServicesById() {
        return  ResponseEntity.ok(service.getStandardServiceForStore());
    }

    @PostMapping("/special-service/create")
    @PreAuthorize("hasAuthority('store:create')")
    public ResponseEntity<LaundryInfoDTO> createSpecialService(@RequestBody SpecialServiceRequest request) {
        return ResponseEntity.ok(service.CreateSpecialServiceByStoreId(request));
    }

    @PostMapping("/standard-service/create")
    @PreAuthorize("hasAuthority('store:create')")
    public ResponseEntity<LaundryInfoDTO> createStandardService(@RequestBody StandardServiceRequest request) {
        return ResponseEntity.ok(service.createStandardService(request));
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
        return ResponseEntity.noContent().build();
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
        return ResponseEntity.noContent().build();

    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('store:create'")
    public ResponseEntity<StoreInfoDTO> createStore(@RequestBody StoreRegisterRequest request){
       return ResponseEntity.ok(storeService.createStore(request));
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('store:read'")
    public ResponseEntity<StoreInfoDTO> getStore(){
        return ResponseEntity.ok(storeService.getCurrentStore());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('store:update'")
    public ResponseEntity<StoreInfoDTO> updateStore(@RequestBody StoreInfoDTO request){
        return ResponseEntity.ok(storeService.updateStore(request));
    }




}
