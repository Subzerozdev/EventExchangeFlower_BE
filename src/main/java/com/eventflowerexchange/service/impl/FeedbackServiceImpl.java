package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.FeedbackRequestDTO;
import com.eventflowerexchange.dto.response.FeedbackResponse;
import com.eventflowerexchange.entity.Feedback;
import com.eventflowerexchange.entity.Shop;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.FeedbackRepository;
import com.eventflowerexchange.repository.ShopRepository;
import com.eventflowerexchange.service.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ShopRepository shopRepository;

    @Override
    public Feedback createNewFeedback(FeedbackRequestDTO feedbackRequestDTO, User user) {
        Shop shop = shopRepository.findById(feedbackRequestDTO.getShopID()).orElseThrow(
                () -> new EntityNotFoundException("Shop not found")
        );
        Feedback feedback = new Feedback();
        feedback.setContent(feedbackRequestDTO.getContent());
        feedback.setRating(feedbackRequestDTO.getRating());
        feedback.setCustomer(user);
        feedback.setShop(shop);
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<FeedbackResponse> getFeedback(String shopId) {
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByShopId(shopId);
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
        feedbacks.forEach(feedback -> {
            FeedbackResponse feedbackResponse = FeedbackResponse.builder()
                    .id(feedback.getId())
                    .content(feedback.getContent())
                    .rating(feedback.getRating())
                    .email(feedback.getCustomer().getEmail())
                    .build();
            feedbackResponses.add(feedbackResponse);
        });
        return feedbackResponses;
    }
}
