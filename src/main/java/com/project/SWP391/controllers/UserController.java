package com.project.SWP391.controllers;
import com.project.SWP391.requests.SpecialServiceFilterRequest;
import com.project.SWP391.requests.SpecialServiceRequest;
import com.project.SWP391.responses.dto.StoreInfoDTO;
import com.project.SWP391.responses.dto.UserInfoDTO;
import com.project.SWP391.services.StoreService;
import com.project.SWP391.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('USER')")
@CrossOrigin
@RequiredArgsConstructor
public class UserController
{
    private final UserService service ;

    @GetMapping("/profile")
    public ResponseEntity<UserInfoDTO> getProfile() {
        return ResponseEntity.ok(service.getCurrentUser());
    }

    @GetMapping("/profile/update/{id}")
    public ResponseEntity<UserInfoDTO> updateProfile(@PathVariable Long id , @RequestBody UserInfoDTO request) {
        return ResponseEntity.ok(service.updateUser(id,request));
    }



}
