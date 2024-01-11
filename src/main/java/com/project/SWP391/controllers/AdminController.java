package com.project.SWP391.controllers;

import com.project.SWP391.entities.Cloth;
import com.project.SWP391.requests.SpecialServiceRequest;

import com.project.SWP391.responses.dto.ClothDTO;
import com.project.SWP391.responses.dto.MaterialDTO;
import com.project.SWP391.responses.dto.UserInfoDTO;

import com.project.SWP391.services.ClothService;
import com.project.SWP391.services.MaterialService;
import com.project.SWP391.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController

@RequestMapping("/api/v1/admin")
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final UserService service;
    private final MaterialService materialService;
    private final ClothService clothService;



    @GetMapping("/user/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<UserInfoDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<UserInfoDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUser(id));
    }

    @PutMapping("/user/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UserInfoDTO> disableUser(@PathVariable Long id, int status) {
        return ResponseEntity.ok(service.updateUserForAdmin(id, status));
    }

    @DeleteMapping("/user/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<UserInfoDTO> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }




    @GetMapping("/store/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllStores() {
        return "GET:: admin controller";
    }




    @GetMapping("/material/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<MaterialDTO>> getAllMaterials() {
        return ResponseEntity.ok(materialService.getAllMaterials());
    }
    @PostMapping("/material/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<MaterialDTO> createNewMaterial(@RequestBody MaterialDTO request) {
        return ResponseEntity.ok(materialService.createNewMaterial(request));
    }

    @PutMapping("/material/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Hidden
    public ResponseEntity<MaterialDTO> updateMaterial(@PathVariable Long id, @RequestBody String request) {
        return ResponseEntity.ok(materialService.updateMaterial(request,id));
    }


    @DeleteMapping("/material/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public ResponseEntity<MaterialDTO> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/cloth/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<ClothDTO>> getAllClothes() {
        return ResponseEntity.ok(clothService.getAllCloth());
    }

    @PostMapping("/cloth/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ClothDTO> getCloth(@RequestBody ClothDTO request) {
        return ResponseEntity.ok(clothService.createNewCloth(request));
    }

    @PutMapping("/cloth/update")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ClothDTO> updateCloth(@RequestBody String request , @PathVariable Long id) {
        return ResponseEntity.ok(clothService.updateCloth(id,request));
    }

    @PostMapping("/time-category/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public String createNewTimeCategory() {
        return "GET:: admin controller";
    }



    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }
}
