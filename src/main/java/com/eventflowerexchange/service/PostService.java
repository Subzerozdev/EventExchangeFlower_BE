package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.PostImageDTO;
import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.dto.response.PostResponse;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.entity.PostImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface PostService {
//    Post createPost(PostRequestDTO postRequestDTO, String userID) throws Exception;

    Post createPost(PostRequestDTO postRequestDTO, String userID, List<Long> typeID) throws Exception;
    Post getPostById(Long id) throws Exception;
//    Page<PostResponse> getAllPosts(PageRequest pageRequest);
    Page<Post> getAllPosts(Map<String, Object> params);
    Post updatePost(Long id, PostRequestDTO postRequestDTO, String userID, List<Long> typeID) throws Exception;
    void deletePost(Long id);
    boolean existsByName(String name);
    PostImage createPostImage(
            Long productId,
            PostImageDTO postImageDTO) throws Exception;
}
