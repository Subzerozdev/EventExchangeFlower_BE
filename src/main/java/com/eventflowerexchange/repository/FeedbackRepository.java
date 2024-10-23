package com.eventflowerexchange.repository;

import com.eventflowerexchange.dto.response.FeedbackResponse;
import com.eventflowerexchange.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT new com.eventflowerexchange.dto.response.FeedbackResponse(f.id,f.content,f.rating,a.email)"+
            "FROM Feedback f join User a ON f.shop.id = a.id where f.shop.id =:shopID")
    List<FeedbackResponse> findFeedbackByShopId(@Param("shopID") String shopID);
}
