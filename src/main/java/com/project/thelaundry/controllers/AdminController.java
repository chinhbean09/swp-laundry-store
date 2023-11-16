package com.project.thelaundry.controllers;


import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/user/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllUsers() {
        return "GET:: admin controller";
    }

    @GetMapping("/store/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllStores() {
        return "GET:: admin controller";
    }

    @GetMapping("/type/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllTypes() {
        return "GET:: admin controller";
    }

    @GetMapping("/material/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllMaterials() {
        return "GET:: admin controller";
    }

    @GetMapping("/time-category/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllTimeCategories() {
        return "GET:: admin controller";
    }


    @PostMapping("/type/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public String createNewType() {
        return "GET:: admin controller";
    }

    @PostMapping("/material/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public String createNewMaterial() {
        return "GET:: admin controller";
    }

    @PostMapping("/time-category/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public String createNewTimeCategory() {
        return "GET:: admin controller";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    @Hidden
    public String put() {
        return "PUT:: admin controller";
    }


    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }
}
