package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.dto.response.PostListResponse;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class PostAPI {
    private final PostService postService;
    private final JwtService jwtService;

    @PostMapping("/api/seller/posts")
    public ResponseEntity<?> createPost(
            @Valid @RequestBody PostRequestDTO postRequestDTO,
            @RequestHeader("Authorization") String jwt,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                System.out.println("error");
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            String userID = jwtService.getUserIdFromJwtToken(jwt);
            Post newPost = postService.createPost(postRequestDTO, userID, postRequestDTO.getTypeId());
            return ResponseEntity.ok(newPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("")
//    public ResponseEntity<PostListResponse> getPosts(
//            @RequestParam("page") int page,
//            @RequestParam("limit") int limit
//    ) {
//        // Tạo Pageable từ thông tin trang và giới hạn
//        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("startDate").descending());
//
//        Page<PostResponse> productPage = postService.getAllPosts(pageRequest);
//
//        // lấy tổng số trang
//        int totalPages = productPage.getTotalPages();
//
//        List<PostResponse> posts = productPage.getContent();
//        return ResponseEntity.ok(PostListResponse.builder()
//                .posts(posts)
//                .totalPages(totalPages)
//                .build());
//    }

    @GetMapping("/posts")
    public ResponseEntity<Object> getPosts(
            @RequestParam Map<String, Object> params
    ) {
        Page<Post> postsPage = postService.getAllPosts(params);
        int totalPages = postsPage.getTotalPages();
        List<Post> posts = postsPage.getContent();
        PostListResponse postListResponse = PostListResponse.builder()
                .posts(posts)
                .totalPages(totalPages)
                .build();
        return new ResponseEntity<>(postListResponse, HttpStatus.OK);
    }

    //http://localhost:8088/api/v1/products/6
    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable Long id) throws Exception {
        Post post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/api/seller/posts/{id}")
    public ResponseEntity<String> deletePost(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) {
        String sellerID = jwtService.getUserIdFromJwtToken(jwt);
        boolean result = postService.deletePost(id, sellerID);
        if (result){
            return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
        } else {
            return ResponseEntity.badRequest().body(String.format("Product with id = %d failed to delete", id));
        }
    }

    @PutMapping("/api/seller/posts/{id}")
    public ResponseEntity<Object> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDTO postRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        String userID = jwtService.getUserIdFromJwtToken(jwt);
        Post post = postService.updatePost(id, postRequestDTO, userID, postRequestDTO.getTypeId());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Get All Posts of Seller
    @GetMapping("/api/seller/posts")
    public ResponseEntity<Object> getSellerPosts(
            @RequestHeader("Authorization") String jwt
    ) {
        String sellerID = jwtService.getUserIdFromJwtToken(jwt);
        List<Post> posts = postService.getSellerPosts(sellerID);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
