package com.project.SWP391.responses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LaundryInfoDTO {
    private Long id;
    private StoreInfoDTO store ;
    private String name;
    private String imageBanner;
    private String description;

    private List<LaundryDetailInfoDTO> details;

    private Float price;

    private List<MaterialDTO> materials;
    private ClothDTO cloth;



    private Boolean isStandard ;

}
