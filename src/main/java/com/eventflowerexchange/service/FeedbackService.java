package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.FeedbackRequestDTO;
import com.eventflowerexchange.dto.response.FeedbackResponse;
import com.eventflowerexchange.entity.Feedback;
import com.eventflowerexchange.entity.User;

import java.util.List;

public interface FeedbackService {
    Feedback createNewFeedback(FeedbackRequestDTO feedbackRequestDTO, User user);
    List<FeedbackResponse> getFeedback(String shopId );
}
