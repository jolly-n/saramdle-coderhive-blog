package net.blogteamthreecoderhivebe.repository;

import net.blogteamthreecoderhivebe.entity.Post;
import net.blogteamthreecoderhivebe.repository.querydsl.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends PostRepositoryCustom, JpaRepository<Post, Long> {
   List<Post> findAllByMember_Id(Long memberId);
}
