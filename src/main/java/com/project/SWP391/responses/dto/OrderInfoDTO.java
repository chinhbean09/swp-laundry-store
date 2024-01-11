package com.project.SWP391.responses.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

public class OrderInfoDTO {

    private Long id;

    private String orderCode;

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSD")
    private Date orderDate;

    private String noteText;

    private float total;

    private int status;

    Set<ItemInfoDTO> items ;

}
