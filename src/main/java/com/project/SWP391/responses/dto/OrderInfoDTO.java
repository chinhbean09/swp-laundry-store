package com.project.SWP391.responses.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDTO {

    private Long id;


    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private String orderDate;

    private String orderCode;
      private StoreInfoDTO store;

    private UserInfoDTO user;

    private String noteText;

    private Float total;

    private int status;

    private int isPaid;

    private StoreTimeDTO time;

    Set<PaymentDTO> payments;

    Set<ItemInfoDTO> items ;


}
