package net.blogteamthreecoderhivebe.dto;

import lombok.Builder;
import net.blogteamthreecoderhivebe.entity.Post;
import net.blogteamthreecoderhivebe.entity.constant.PostCategory;
import net.blogteamthreecoderhivebe.entity.constant.PostStatus;

import java.util.List;

@Builder
public record PostDto(
        Long id,
        MemberDto memberDto,
        LocationDto locationDto,
        String title,
        String content,
        String thumbImageUrl,
        PostCategory postCategory,
        String platforms,
        PostStatus postStatus,
        JobDto jobDto,
        List<String> skills,
        int likes

) {
    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .memberDto(MemberDto.from(post.getMember()))
                .locationDto(LocationDto.from(post.getLocation()))
                .title(post.getTitle())
                .content(post.getContent())
                .thumbImageUrl(post.getThumbImageUrl())
                .postCategory(post.getPostCategory())
                .platforms(post.getPlatforms())
                .postStatus(post.getPostStatus())
                .jobDto(JobDto.from(post.getJob()))
                .likes(post.getLikingMembers().size())
                .build();
    }

    public static PostDto from(Post post, List<String> skills) {
        return PostDto.builder()
                .id(post.getId())
                .memberDto(MemberDto.from(post.getMember()))
                .locationDto(LocationDto.from(post.getLocation()))
                .title(post.getTitle())
                .content(post.getContent())
                .thumbImageUrl(post.getThumbImageUrl())
                .postCategory(post.getPostCategory())
                .platforms(post.getPlatforms())
                .postStatus(post.getPostStatus())
                .jobDto(JobDto.from(post.getJob()))
                .skills(skills)
                .likes(post.getLikingMembers().size())
                .build();
    }

    public Post toEntity() {
        return Post.builder()
                .id(id)
                .member(memberDto.toEntity())
                .location(locationDto.toEntity())
                .title(title)
                .content(content)
                .thumbImageUrl(thumbImageUrl)
                .postCategory(postCategory)
                .platforms(platforms)
                .postStatus(postStatus)
                .job(jobDto.toEntity())
                .build();
    }
}
