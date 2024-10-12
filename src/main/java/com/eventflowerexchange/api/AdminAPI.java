package com.eventflowerexchange.api;

import com.eventflowerexchange.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAPI {
    private final PostService postService;

    @PutMapping("/posts/{id}/{status}")
    public ResponseEntity<Object> updatePostStatus(
            @PathVariable("id") Long id,
            @PathVariable("status") Boolean status
    ) throws Exception {
        postService.updatePostStatus(id, status);
        return new ResponseEntity<>("Successfully Update Status", HttpStatus.OK);
    }
}
