package com.project.SWP391.requests;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecialServiceFilterRequest {

    private String name;
    private float price;

    private List<Long> materials;

    private Long clothId;

    private String district;
}
