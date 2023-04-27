package net.blogteamthreecoderhivebe.global.auth.dto;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collections;

import static net.blogteamthreecoderhivebe.domain.member.constant.MemberRole.USER;

@Getter
public class MemberPrincipal extends DefaultOAuth2User {
    private String email;

    public MemberPrincipal(SocialLoginDto socialLoginDto) {
        super(
                // 지금은 인증만 하고 권한을 다루고 있지 않아서 임의로 세팅
                Collections.singleton(new SimpleGrantedAuthority(USER.getDescription())),
                socialLoginDto.attributes(),
                socialLoginDto.nameAttributeKey()
        );
        this.email = socialLoginDto.email();
    }
}
