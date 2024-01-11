package com.project.SWP391.responses.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserInfoDTO {
    private Long id;
    private String email;
    private String phone;
    private String fullName;
    private String address;
    private String image;
    private int status;

    private String role;
}
