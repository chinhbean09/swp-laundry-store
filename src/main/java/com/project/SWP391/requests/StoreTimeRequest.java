package com.project.SWP391.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreTimeRequest {
    private float price;
    private String dateRange;

    private Long timeCategoryId;
}
