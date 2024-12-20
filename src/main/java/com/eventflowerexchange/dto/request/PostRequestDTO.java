package com.eventflowerexchange.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String name;
    private String description;
    private String thumbnail;
    private String address;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> imageUrls;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 100000000, message = "Price must be less than or equal to 100,000,000")
    private Long price;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("type_id")
    private List<Long> typeId;
}
