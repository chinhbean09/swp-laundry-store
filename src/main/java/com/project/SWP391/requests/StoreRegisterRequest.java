package com.project.SWP391.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreRegisterRequest {

    private String name;
    private String address;
    private String district;
    private String phone;
}
