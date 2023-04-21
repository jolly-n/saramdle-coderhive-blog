package net.blogteamthreecoderhivebe.entity;

import net.blogteamthreecoderhivebe.repository.JobRepository;
import net.blogteamthreecoderhivebe.repository.LocationRepository;
import net.blogteamthreecoderhivebe.repository.MemberRepository;
import net.blogteamthreecoderhivebe.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class AuditingFieldsTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    LocationRepository locationRepository;

    @DisplayName("회원 등록시 생성일, 수정일, 생성자, 수정자 자동으로 생성")
    @Test
    void member() {
        // given
        Member member = Member.builder().email("test@test.com").build();

        // when
        memberRepository.save(member);

        // then
        assertThat(member.getCreatedAt()).isNotNull();
        assertThat(member.getModifiedAt()).isNotNull();
        assertThat(member.getCreatedBy()).isNotNull();
        assertThat(member.getModifiedBy()).isNotNull();
    }

    @DisplayName("게시물 등록시 생성일, 수정일, 생성자, 수정자가 자동으로 생성")
    @Test
    void post() {
        // given
        Member member = Member.builder().email("test@test.com").build();
        memberRepository.save(member);

        Job job = jobRepository.findById(1L).get();
        Location location = locationRepository.findById(1L).get();

        Post post = Post.builder()
                .member(member)
                .location(location)
                .job(job)
                .build();

        Post savedPost = postRepository.save(post);

        // when
        Post findPost = postRepository.findById(savedPost.getId()).get();

        // then
        assertThat(findPost.getCreatedAt()).isNotNull();
        assertThat(findPost.getModifiedAt()).isNotNull();
        assertThat(findPost.getCreatedBy()).isNotNull();
        assertThat(findPost.getModifiedBy()).isNotNull();
    }
}
