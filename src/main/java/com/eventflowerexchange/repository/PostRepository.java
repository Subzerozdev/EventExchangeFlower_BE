package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>,
        JpaSpecificationExecutor<Post> {
    Post findPostById(Long id);
    List<Post> findPostsByUserId(String sellerID);
    Page<Post> findAll(Pageable pageable);
    List<Post> findPostsByStartDateIsBefore(LocalDateTime expiredDate);
}
