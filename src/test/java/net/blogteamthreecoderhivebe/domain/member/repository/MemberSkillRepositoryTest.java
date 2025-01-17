package net.blogteamthreecoderhivebe.domain.member.repository;

import net.blogteamthreecoderhivebe.global.config.TestJpaConfig;
import net.blogteamthreecoderhivebe.global.config.TestQueryDslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DisplayName("MemberSkillRepository 테스트")
@Import({TestJpaConfig.class, TestQueryDslConfig.class})
@Transactional
@DataJpaTest
class MemberSkillRepositoryTest {
    @Autowired
    private MemberSkillRepository memberSkillRepository;

    @DisplayName("멤버 id 로 skills 정보 가져오기")
    @Test
    void getSkills() {
        List<String> strings = memberSkillRepository.searchSkill(1L);
        System.out.println(strings);
    }
}
