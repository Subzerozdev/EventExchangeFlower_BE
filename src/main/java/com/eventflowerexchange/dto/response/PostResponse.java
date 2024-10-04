package com.eventflowerexchange.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse  {
    private String name;
    private String description;
    private String thumbnail;
    private String address;
    private Float price;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("user_id")
    private Long userId;

}
