package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.PostImageDTO;
import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.entity.PostImage;
import com.eventflowerexchange.entity.Type;
import com.eventflowerexchange.repository.PostRepository;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.PostService;
import com.eventflowerexchange.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostAPI {
    private final PostService postService;
    private final JwtService jwtService;
    private final TypeService typeService;

    @PostMapping("")
    //POST http://localhost:8088/api/products
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
            // Khánh mới thêm vào
            Post newPost = postService.createPost(postRequestDTO, userID,postRequestDTO.getTypeId());
            return ResponseEntity.ok(newPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //POST http://localhost:8088/v1/api/products
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long postId,
            @ModelAttribute("files") List<MultipartFile> files
    ) {
        try {
            Post existingPost = postService.getPostById(postId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if (files.size() > PostImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
            }
            List<PostImage> postImages = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                PostImage productImage = postService.createPostImage(
                        existingPost.getId(),
                        PostImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                postImages.add(productImage);
            }
            return ResponseEntity.ok().body(postImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image file format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    // kiểm tra đây có phải là file ảnh
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
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

    @GetMapping("")
    public ResponseEntity<Page<Post>> getPosts(
            @RequestParam Map<String, Object> params
    ) {
        Page<Post> posts = postService.getAllPosts(params);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //http://localhost:8088/api/v1/products/6
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable Long id) throws Exception {
        Post post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDTO postRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        String userID = jwtService.getUserIdFromJwtToken(jwt);
        Post post = postService.updatePost(id, postRequestDTO, userID,postRequestDTO.getTypeId());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
