package com.project.SWP391.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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

    private String imageBanner;


}
