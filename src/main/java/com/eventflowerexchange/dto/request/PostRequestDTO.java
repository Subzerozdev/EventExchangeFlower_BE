package com.eventflowerexchange.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    private String title;
    private String description;
    private String address;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String image;
    private Long price;
    private int discount;
    private Long category_id;
}
