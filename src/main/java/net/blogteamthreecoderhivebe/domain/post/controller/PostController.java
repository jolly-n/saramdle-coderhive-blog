package net.blogteamthreecoderhivebe.domain.post.controller;

import lombok.RequiredArgsConstructor;
import net.blogteamthreecoderhivebe.domain.post.dto.request.PostRequestDto;
import net.blogteamthreecoderhivebe.domain.post.dto.response.PostResponseDto;
import net.blogteamthreecoderhivebe.domain.post.dto.response.PostSearchResponse;
import net.blogteamthreecoderhivebe.domain.post.service.PostService;
import net.blogteamthreecoderhivebe.global.auth.dto.MemberPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PostResponseDto.Save register(@RequestBody PostRequestDto.Save dto,
                                         @AuthenticationPrincipal MemberPrincipal principal) {
        return postService.save(dto, principal.getEmail());
    }

    @GetMapping
    public Page<PostSearchResponse> search(@RequestParam Long memberId,
                                           @ModelAttribute PostRequestDto.SearchCond searchCond,
                                           Pageable pageable) {
        return postService.searchPosts(memberId, searchCond, pageable);
    }

    @GetMapping("/{postId}")
    public PostResponseDto.Detail detail(@PathVariable Long postId,
                                         @AuthenticationPrincipal MemberPrincipal principal) {
        return postService.findPost(postId, principal.getEmail());
    }

    @PutMapping("/{postId}")
    public PostResponseDto.Edit edit(@PathVariable Long postId,
                                     @RequestBody PostRequestDto.Edit dto) {
        // todo: 현재 로그인된 회원이 작성한 글인지 확인하는 로직 구현

        return postService.edit(postId, dto);
    }
}
