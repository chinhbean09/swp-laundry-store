package com.project.thelaundry.requests;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecialServiceRequest {




    private String name;
    private String description;

    private String imageBanner;
    private List<Long> materials;

    private float price;

    private String unit;

    private Long cloth;

    private int isDeleted;


}
