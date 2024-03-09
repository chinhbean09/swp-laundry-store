package com.project.SWP391.requests;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderUpdateRequest {

    private Integer status;
    //private Float weight;
}
