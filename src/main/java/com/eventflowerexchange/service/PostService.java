package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.PostImageDTO;
import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.dto.response.PostResponse;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.entity.PostImage;
import org.hibernate.sql.results.jdbc.internal.JdbcValuesMappingProducerStandard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PostService {
    Post createPost(PostRequestDTO postRequestDTO) throws Exception;

    Post getPostById(long id) throws Exception;

    Page<PostResponse> getAllPosts(PageRequest pageRequest);

    Post updatePost(long id, PostRequestDTO postRequestDTO) throws Exception;

    void deletePost(long id);

    boolean existsByName(String name);

    PostImage createPostImage(
            Long productId,
            PostImageDTO postImageDTO) throws Exception;
}
