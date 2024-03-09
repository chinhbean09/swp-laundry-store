package com.project.SWP391.requests;

import com.project.SWP391.requests.dto.ItemReqDTO;
import lombok.*;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderRequest {

    private List<ItemReqDTO> items ;
    private Float total ;

    private Long storeId;

   // private Long userId ;
    private Long storeTimeId;

    private Long createDate;




}
