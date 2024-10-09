package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.PostImageDTO;
import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.exception.DataNotFoundException;
import com.eventflowerexchange.exception.InvalidParamException;
import com.eventflowerexchange.mapper.PostMapper;
import com.eventflowerexchange.repository.*;
import com.eventflowerexchange.service.PostService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        // Save to DB and return
        return postRepository.save(newPost);
    }

    @Override
    public Post getPostById(Long postId) throws Exception {
//        return postRepository.findById(postId).
//                orElseThrow(() -> new DataNotFoundException(
//                        "Cannot find product with id =" + postId));
        return postRepository.findPostById(postId);
    }

//    @Override
//    public Page<PostResponse> getAllPosts(PageRequest pageRequest) {
//        // Lấy danh sách sản phẩm theo trang(page) và giới hạn(limit)
//        return postRepository.findAll(pageRequest).map(post -> {
//            PostResponse postResponse = PostResponse.builder()
//                    .name(post.getName())
//                    .price(post.getPrice())
//                    .thumbnail(post.getThumbnail())
//                    .description(post.getDescription())
//                    .address(post.getAddress())
//                    .startDate(post.getStartDate())
//                    .endDate(post.getEndDate())
//                    .categoryId(post.getCategory().getId())
//                    .build();
//            return postResponse;
//        });
//    }

    @Override
    public Page<Post> getAllPosts(
            Map<String, Object> params
//            String eventCategory, String flowerType,
//            Long minPrice, Long maxPrice,
//            String sort, Integer pageNumber
    ) {
        Specification<Post> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!params.get("categoryID").toString().isEmpty()) {
                Join<Post, Category> postCategoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(postCategoryJoin.get("id"), params.get("categoryID")));
            }
//            if (flowerType != null) {
//                Join<Post,Category> postCategoryJoin = root.join("category");
//                predicates.add(criteriaBuilder.equal(postCategoryJoin.get("categoryID"), eventCategory));
//            }
            if (params.get("minPrice") != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Long.valueOf(params.get("minPrice").toString())));
            }
            if (params.get("maxPrice") != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Long.valueOf(params.get("maxPrice").toString())));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        Pageable pageable;
        String sort = params.get("sort").toString();
        int pageNumber = params.get("pageNumber").toString().isEmpty() ? 0 :
                Integer.parseInt(params.get("pageNumber").toString());
        if (sort != null && !sort.isEmpty()) {
            pageable = switch (sort) {
                case "price_low" -> PageRequest.of(pageNumber, 10, Sort.by("price").descending());
                case "price_high" -> PageRequest.of(pageNumber, 10, Sort.by("price").ascending());
                default -> PageRequest.of(pageNumber, 10, Sort.unsorted());
            };
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.unsorted());
        }
        return postRepository.findAll(specification, pageable);
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
    public void deletePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        optionalPost.ifPresent(postRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return postRepository.existsByName(name);
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
}
