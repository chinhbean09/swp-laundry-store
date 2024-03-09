package com.project.SWP391.requests;

import com.project.SWP391.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String fullName;
    private String phone;
    private String email;
    private String password;
    private Role role;

    private String address;

}