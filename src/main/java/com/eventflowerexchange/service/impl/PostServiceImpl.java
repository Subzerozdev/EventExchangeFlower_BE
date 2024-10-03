package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.PostImageDTO;
import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.dto.response.PostResponse;
import com.eventflowerexchange.entity.Category;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.entity.PostImage;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.exception.DataNotFoundException;
import com.eventflowerexchange.exception.InvalidParamException;
import com.eventflowerexchange.repository.CategoryRepository;
import com.eventflowerexchange.repository.PostImageRepository;
import com.eventflowerexchange.repository.PostRepository;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private  final PostRepository postRepository;
    private  final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Post createPost(PostRequestDTO postRequestDTO) throws Exception {
        Category existingCategory = categoryRepository
                .findById(postRequestDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: "+postRequestDTO.getCategoryId()));

        User existingUser = userRepository
                .findById(postRequestDTO.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find user with id: "+postRequestDTO.getUserId()));

        Post newPost = Post.builder()
                .name(postRequestDTO.getName())
                .price(postRequestDTO.getPrice())
                .thumbnail(postRequestDTO.getThumbnail())
                .address(postRequestDTO.getAddress())
                .description(postRequestDTO.getDescription())
                .category(existingCategory)
                .user(existingUser)
                .build();

            return postRepository.save(newPost);
    }

    @Override
    public Post getPostById(long postId) throws Exception {
        return postRepository.findById(postId).
                orElseThrow(()-> new DataNotFoundException(
                        "Cannot find product with id ="+postId));
    }

    @Override
    public Page<PostResponse> getAllPosts(PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang(page) và giới hạn(limit)
        return postRepository.findAll(pageRequest).map (post -> {
            PostResponse postResponse = PostResponse.builder()
                    .name(post.getName())
                    .price(post.getPrice())
                    .thumbnail(post.getThumbnail())
                    .description(post.getDescription())
                    .address(post.getAddress())
                    .categoryId(post.getCategory().getId())
                    .build();
            postResponse.setCreatedAt(post.getCreatedAt());
            postResponse.setUpdatedAt(post.getUpdatedAt());
            return postResponse;
        });
    }

    @Override
    public Post updatePost(long id, PostRequestDTO postRequestDTO) throws Exception {
        Post existingPost = getPostById(id);
        if(existingPost != null) {
            //copy các thuộc tính từ DTO -> Post
            //Có thể sử dụng ModelMapper
            Category existingCategory = categoryRepository
                    .findById(postRequestDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: "+postRequestDTO.getCategoryId()));

            User existingUser = userRepository
                    .findById(postRequestDTO.getUserId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find user with id: "+postRequestDTO.getUserId()));
            existingPost.setName(postRequestDTO.getName());
            existingPost.setCategory(existingCategory);
            existingPost.setUser(existingUser);
            existingPost.setPrice(postRequestDTO.getPrice());
            existingPost.setDescription(postRequestDTO.getDescription());
            existingPost.setThumbnail(postRequestDTO.getThumbnail());
            existingPost.setAddress(postRequestDTO.getAddress());
            return postRepository.save(existingPost);
        }
        return null;

    }

    @Override
    public void deletePost(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        optionalPost.ifPresent(postRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return postRepository.existsByName(name);
    }

    @Override
    public PostImage createPostImage(Long productId, PostImageDTO postImageDTO) throws Exception { Post existingPost = postRepository
            .findById(productId)
            .orElseThrow(() ->
                    new DataNotFoundException(
                            "Cannot find product with id: "+postImageDTO.getPostId()));
        PostImage newProductImage = PostImage.builder()
                .post(existingPost)
                .imageUrl(postImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = postImageRepository.findByPostId(productId).size();
        if(size >= PostImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of images must be <=" +
                    " "+PostImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return postImageRepository.save(newProductImage);
    }
}
