package com.project.SWP391.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialServiceResponseInItem {
    private Long id;
    private String name;

    private String imageBanner;
    private String description;

    private float price;

    private String unit;

    private List<Long> materials;

    private Long cloth;
}
