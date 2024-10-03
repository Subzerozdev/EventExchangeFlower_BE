package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByName(String name);
    Page<Post> findAll(Pageable pageable);  // phân trang
}
