package com.project.SWP391.responses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {

    private Long id;
    private UserInfoDTO user;
    private String content;
    private int star;

    private LaundryInfoDTO laundryService;
}
