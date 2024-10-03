package com.eventflowerexchange.dto.response;

import lombok.*;

import java.util.List;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListResponse {
    private List<PostResponse> products;
    private int totalPages;
}
