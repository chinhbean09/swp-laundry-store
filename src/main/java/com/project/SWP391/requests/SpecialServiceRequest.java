package com.project.SWP391.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.SWP391.responses.dto.LaundryDetailInfoDTO;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecialServiceRequest {


    private Long storeId;

    private String name;
    private String description;

    private String imageBanner;
    private List<Long> materials;

    private LaundryDetailInfoDTO details;

    private Long clothId;

    private int isDeleted;


}
