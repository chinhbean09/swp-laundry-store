package com.project.SWP391.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.SWP391.entities.Feedback;
import com.project.SWP391.responses.dto.FeedbackDTO;
import com.project.SWP391.responses.dto.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private UserInfoDTO userInfoDTO;
    private List<FeedbackDTO> feedbacks;
    private String role;
}