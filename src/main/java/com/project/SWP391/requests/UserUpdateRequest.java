package com.project.SWP391.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String phone;
    private String fullName;
    private String address;
}
