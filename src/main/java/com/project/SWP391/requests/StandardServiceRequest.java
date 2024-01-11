package com.project.SWP391.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.SWP391.responses.dto.LaundryDetailInfoDTO;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardServiceRequest {
    @JsonIgnore
    private Long id;
    private String name;
    private String description;
    private List<LaundryDetailInfoDTO> details;
    private String imageBanner;


}
