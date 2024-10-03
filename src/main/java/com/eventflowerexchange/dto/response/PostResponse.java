package com.eventflowerexchange.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse extends  BaseResponse  {


    private String name;

    private String description;

    private String thumbnail;

    private String address;



    private Float price;


    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("user_id")
    private Long userId;

}
