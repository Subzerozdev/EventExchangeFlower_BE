package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.FeedbackRequestDTO;
import com.eventflowerexchange.dto.response.FeedbackResponse;
import com.eventflowerexchange.entity.Feedback;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.service.FeedbackService;
import com.eventflowerexchange.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackAPI {
    private final JwtService jwtService;
    private final FeedbackService feedbackService;
    @PostMapping()
    public ResponseEntity createFeedback(
            @RequestHeader("Authorization") String jwt,
            @RequestBody FeedbackRequestDTO feedbackRequestDTO
    ) {
        User user =jwtService.getUserFromJwtToken(jwt);
        Feedback feedback = feedbackService.createNewFeedback(feedbackRequestDTO, user);
        return ResponseEntity.ok(feedback);
    }
    @GetMapping("/{id}")
    public ResponseEntity getAllFeedback(
            @PathVariable("id") String id
    )
    {
        List<FeedbackResponse> feedback =feedbackService.getFeedback(id);
        return ResponseEntity.ok(feedback);
    }
}
