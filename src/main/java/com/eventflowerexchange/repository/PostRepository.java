package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Post;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>,
        JpaSpecificationExecutor<Post> {
    boolean existsByName(String name);
    Post findPostById(Long id);
    Page<Post> findAll(Pageable pageable);  // phân trang
}
