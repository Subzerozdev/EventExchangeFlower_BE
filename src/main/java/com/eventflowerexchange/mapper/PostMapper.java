package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.request.PostRequestDTO;
import com.eventflowerexchange.dto.response.PostResponse;
import com.eventflowerexchange.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(PostRequestDTO postRequestDTO);
    void updatePost(@MappingTarget Post post, PostRequestDTO postRequestDTO);
    PostResponse toPostResponse(Post post);
}
