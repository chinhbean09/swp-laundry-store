package com.project.SWP391.responses.dto;

import com.project.SWP391.responses.SpecialServiceResponseInItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoDTO {
    private Long id;
    private float total;
    private int quantity;
    private float weight;
    private LaundryInfoDTO laundryServices;

}
