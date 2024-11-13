package com.eventflowerexchange.dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListResponse {
    private List<PostResponse> posts;
}
