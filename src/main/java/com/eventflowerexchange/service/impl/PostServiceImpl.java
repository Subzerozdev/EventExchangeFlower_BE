package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.PostImageDTO;
import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.exception.DataNotFoundException;
import com.eventflowerexchange.exception.InvalidParamException;
import com.eventflowerexchange.mapper.PostMapper;
import com.eventflowerexchange.repository.*;
import com.eventflowerexchange.service.PostService;
import com.eventflowerexchange.util.FieldValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TypeRepository typeRepository;
    private final PostMapper postMapper;

    @Override
    public Post createPost(PostRequestDTO postRequestDTO, String userID, List<Long> typeID) throws Exception {
        // Create Post
        Post newPost = postMapper.toPost(postRequestDTO);
        newPost.setStatus(POST_STATUS.PENDING);
        // Get Category by ID
        if (postRequestDTO.getCategoryId() != null) {
            Category existingCategory = categoryRepository
                    .findById(postRequestDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: " + postRequestDTO.getCategoryId()));
            newPost.setCategory(existingCategory);
        }
        // Get User by Id
        User existingUser = userRepository
                .findById(userID)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find user with id: " + userID));
        newPost.setUser(existingUser);
        // Get Type by Id
        if (typeID != null) {
            List<Type> existingType = typeRepository.findByIdIn(typeID);
            if (existingType.isEmpty()) {
                throw new DataNotFoundException("Cannot find types with ids: " + typeID);
            }
            newPost.setTypes(existingType);
        }


        // Save image to DB
        List<PostImage> images = new ArrayList<>();
        if (postRequestDTO.getImageUrls() != null && !postRequestDTO.getImageUrls().isEmpty()) {
            for (String imageUrl : postRequestDTO.getImageUrls()) {
                PostImage postImage = PostImage.builder()
                        .imageUrl(imageUrl)
                        .post(newPost)
                        .build();
                images.add(postImage);
            }
            newPost.setImages(images);
        }
        return postRepository.save(newPost);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findPostById(postId);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getSellerPosts(String sellerID) {
        return postRepository.findPostsByUserId(sellerID);
    }

    @Override
    public Post updatePost(Long id, PostRequestDTO postRequestDTO, String userID, List<Long> typeID) throws Exception {
        Post existingPost = getPostById(id);
        if (existingPost != null) {
            if (postRequestDTO.getCategoryId() != null) {
                Category existingCategory = categoryRepository
                        .findById(postRequestDTO.getCategoryId())
                        .orElseThrow(() ->
                                new DataNotFoundException(
                                        "Cannot find category with id: " + postRequestDTO.getCategoryId()));
                existingPost.setCategory(existingCategory);
            }

            User existingUser = userRepository
                    .findById(userID)
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find user with id: " + userID));

            // Get Type by Id
            if (typeID != null) {
                List<Type> existingType = typeRepository.findByIdIn(typeID);
                if (existingType.isEmpty()) {
                    throw new DataNotFoundException("Cannot find types with ids: " + typeID);
                }
                existingPost.setTypes(existingType);
            }
            // Update Post
            postMapper.updatePost(existingPost, postRequestDTO);
            return postRepository.save(existingPost);
        }
        return null;
    }

    @Override
    public boolean deletePost(Long id, String sellerID) {
        boolean result = false;
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getUser().getId().equals(sellerID)) {
                post.setStatus(POST_STATUS.DELETED);
                postRepository.save(post);
                result = true;
            }
        }
        return result;
    }

    @Override
    public PostImage createPostImage(Long postId, PostImageDTO postImageDTO) throws Exception {
        Post existingPost = postRepository
                .findById(postId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id: " + postImageDTO.getPostId()));
        PostImage newPostImage = PostImage.builder()
                .post(existingPost)
                .imageUrl(postImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm

        int size = postImageRepository.findByPostId(postId).size();
        if (size >= PostImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of images must be <=" +
                    " " + PostImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return postImageRepository.save(newPostImage);
    }

    @Override
    public void updatePostStatus(Long id, Boolean status) {
        // Get Post
        Post post = postRepository.findPostById(id);
        // Check If Post is Existed
        FieldValidation.checkObjectExist(post, "Post");
        // Change status
        if (status) {
            post.setStatus(POST_STATUS.APPROVE);
        } else {
            post.setStatus(POST_STATUS.DISAPPROVE);
        }
        postRepository.save(post);
    }
}
