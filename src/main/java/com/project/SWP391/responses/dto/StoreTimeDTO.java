package com.project.SWP391.responses.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreTimeDTO {
    private Long id;
    private float price;

    private String dateRange;

    private TimeDTO timeCategory;

    private int status;


}
