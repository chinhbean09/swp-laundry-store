package com.project.thelaundry.responses.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreInfoDTO {
    private Long id;
    private String name;
    private String address;
    private String district;
    private String phone;


}
