package com.project.thelaundry.requests;

import com.project.thelaundry.entities.Role;
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

}