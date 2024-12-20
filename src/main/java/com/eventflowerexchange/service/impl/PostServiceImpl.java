package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.exception.DataNotFoundException;
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
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TypeRepository typeRepository;
    private final PostMapper postMapper;

    @Override
    public Post createPost(PostRequestDTO postRequestDTO, String userID, List<Long> typeID) throws Exception {
        Post newPost = postMapper.toPost(postRequestDTO);
        newPost.setStartDate(postRequestDTO.getStartDate().plusDays(1));
        newPost.setEndDate(postRequestDTO.getEndDate().plusDays(1));
        newPost.setStatus(POST_STATUS.PENDING);

        Category category = categoryRepository.findCategoryById(postRequestDTO.getCategoryId());
        FieldValidation.checkObjectExist(category, "Category");
        newPost.setCategory(category);

        User user = userRepository.findUserById(userID);
        FieldValidation.checkObjectExist(user, "User");
        newPost.setUser(user);

        List<Type> types = validateType(typeID);
        newPost.setTypes(types);

        List<PostImage> images = validateImages(newPost, postRequestDTO.getImageUrls());
        newPost.setImages(images);

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

        FieldValidation.checkObjectExist(existingPost, "Post");
        if (existingPost.getUser().getId().equals(userID)) {
            postMapper.updatePost(existingPost, postRequestDTO);
            existingPost.setStatus(POST_STATUS.PENDING);

            Category category = categoryRepository.findCategoryById(postRequestDTO.getCategoryId());
            FieldValidation.checkObjectExist(category, "Category");
            existingPost.setCategory(category);

            existingPost.setTypes(validateType(typeID));

            existingPost.getImages().clear();
            existingPost.getImages().addAll(validateImages(existingPost, postRequestDTO.getImageUrls()));
        }
        return postRepository.save(existingPost);
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
    public Post updatePostStatus(Long id, Boolean status) {
        // Get Post
        Post post = postRepository.findPostById(id);
        // Check If Post is Existed
        FieldValidation.checkObjectExist(post, "Post");
        // Change status
        if (post.getStatus().equals(POST_STATUS.PENDING) || post.getStatus().equals(POST_STATUS.APPROVE)){
            if (status) {
                post.setStatus(POST_STATUS.APPROVE);
            } else {
                post.setStatus(POST_STATUS.DISAPPROVE);
            }
        }
        return postRepository.save(post);
    }

    private List<Type> validateType(List<Long> typeID) throws DataNotFoundException {
        List<Type> existingType = null;
        if (typeID != null) {
            existingType = typeRepository.findByIdIn(typeID);
            if (existingType.isEmpty()) {
                throw new DataNotFoundException("Cannot find types with ids: " + typeID);
            }
        }
        return existingType;
    }

    private List<PostImage> validateImages(Post post, List<String> imageUrls){
        List<PostImage> images = new ArrayList<>();
        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                PostImage postImage = PostImage.builder()
                        .imageUrl(imageUrl)
                        .post(post)
                        .build();
                images.add(postImage);
            }
        }
        return images;
    }
}
