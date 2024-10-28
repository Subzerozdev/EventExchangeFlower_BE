package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.dto.response.PostListResponse;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.PostService;
import com.eventflowerexchange.util.FieldValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/posts")
    public ResponseEntity<Object> getPosts() {
        List<Post> posts = postService.getAllPosts();
        PostListResponse postListResponse = PostListResponse.builder()
                .posts(posts)
                .build();
        return new ResponseEntity<>(postListResponse, HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable Long id) throws Exception {
        Post post = postService.getPostById(id);
        FieldValidation.checkObjectExist(post, "Post");
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

    // Display all posts of seller
    @GetMapping("/api/seller/posts")
    public ResponseEntity<Object> getSellerPosts(
            @RequestHeader("Authorization") String jwt
    ) {
        String sellerID = jwtService.getUserIdFromJwtToken(jwt);
        List<Post> posts = postService.getSellerPosts(sellerID);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<Object> getPostBySellerId(
            @PathVariable String id
    ){
        List<Post> posts = postService.getSellerPosts(id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
