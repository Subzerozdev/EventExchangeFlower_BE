package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.exception.DataNotFoundException;

import java.util.List;

public interface PostService {
    Post createPost(PostRequestDTO postRequestDTO, String userID, List<Long> typeID) throws Exception;
    Post getPostById(Long id) throws Exception;
    List<Post> getAllPosts();
    List<Post> getSellerPosts(String sellerID);
    Post updatePost(Long id, PostRequestDTO postRequestDTO, String userID, List<Long> typeID) throws Exception;
    boolean deletePost(Long id, String sellerID);
    void updatePostStatus (Long id, Boolean status) throws DataNotFoundException;
}
