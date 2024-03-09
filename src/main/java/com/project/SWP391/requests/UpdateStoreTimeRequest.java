package com.project.SWP391.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStoreTimeRequest {
    private Float price;
    private String dateRange;
}
