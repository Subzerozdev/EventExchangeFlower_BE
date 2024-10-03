package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
         List<PostImage> findByPostId(Long postId);

}
