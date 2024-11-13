package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.entity.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse  {
    private Long id;
    private String name;
    private String description;
    private String thumbnail;
    private String address;
    private Float price;
    private POST_STATUS status;
    private Category category;
    private List<Type> types;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    private LocalDateTime endDate;

    @JsonProperty("imageUrls")
    private List<PostImage> images;

    @JsonProperty("shop_name")
    private String shopName;
}
