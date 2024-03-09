package com.project.SWP391.responses.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeDTO {
    private Long id;
    private String name;

    private int status;

}
