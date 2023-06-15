package net.blogteamthreecoderhivebe.domain.post.repository.querydsl;

import jakarta.transaction.Transactional;
import net.blogteamthreecoderhivebe.domain.info.entity.Skill;
import net.blogteamthreecoderhivebe.domain.info.service.SkillService;
import net.blogteamthreecoderhivebe.domain.post.entity.Post;
import net.blogteamthreecoderhivebe.domain.post.repository.PostRepository;
import net.blogteamthreecoderhivebe.domain.post.repository.RecruitmentSkillRepository;
import net.blogteamthreecoderhivebe.domain.post.service.RecruitmentSkillService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class RecruitmentSkillCustomImplTest {
    @Autowired PostRepository postRepository;
    @Autowired RecruitmentSkillRepository recruitmentSkillRepository;
    @Autowired SkillService skillService;
    @Autowired RecruitmentSkillService recruitmentSkillService;

    @DisplayName("게시글 id로 해당 게시글에서 모집하는 기술 목록을 조회")
    @Test
    void findSkillsEqPostId() {
        // given
        List<Long> skillIds = List.of(1L, 2L);
        Post post = postRepository.save(Post.builder().build());
        recruitmentSkillService.save(skillIds, post);

        // when
        List<Skill> findSkills = recruitmentSkillRepository.findSkills(post.getId());

        // then
        assertThat(findSkills.get(0)).isEqualTo(skillService.findOne(skillIds.get(0)));
        assertThat(findSkills.get(1)).isEqualTo(skillService.findOne(skillIds.get(1)));
    }
}
