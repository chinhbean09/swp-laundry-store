package com.project.SWP391.services;

import com.project.SWP391.requests.FeedbackRequest;
import com.project.SWP391.responses.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackService {
    FeedbackDTO createFeedback (FeedbackRequest request);
    void deleteFeedback(Long id);

    List<FeedbackDTO> getAllFeedbackOfService(Long id);


    List<FeedbackDTO> getAllFeedbackOfUser();

}
