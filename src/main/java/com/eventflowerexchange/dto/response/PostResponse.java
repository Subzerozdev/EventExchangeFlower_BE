package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonProperty("end_date")
    private LocalDateTime endDate;
    private POST_STATUS status;
    private Category category;
    @JsonProperty("user_id")
    private String userId;
    private List<Type> types;
    @JsonProperty("imageUrls")
    private List<PostImage> images;

}
