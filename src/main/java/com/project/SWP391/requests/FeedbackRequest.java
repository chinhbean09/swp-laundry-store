package com.project.SWP391.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor

@Getter
@Setter
public class FeedbackRequest
{

    private Long serviceId;
    private String content;
    private Integer star;
}
