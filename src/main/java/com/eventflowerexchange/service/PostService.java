package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.PostImageDTO;
import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.entity.PostImage;
import com.eventflowerexchange.exception.DataNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface PostService {
    Post createPost(PostRequestDTO postRequestDTO, String userID, List<Long> typeID) throws Exception;
    Post getPostById(Long id) throws Exception;
//    Page<PostResponse> getAllPosts(PageRequest pageRequest);
    Page<Post> getAllPosts(Map<String, Object> params);
    List<Post> getSellerPosts(String sellerID);
    Post updatePost(Long id, PostRequestDTO postRequestDTO, String userID, List<Long> typeID) throws Exception;
    boolean deletePost(Long id, String sellerID);
    PostImage createPostImage(Long productId, PostImageDTO postImageDTO) throws Exception;
    void updatePostStatus (Long id, Boolean status) throws DataNotFoundException;
}
