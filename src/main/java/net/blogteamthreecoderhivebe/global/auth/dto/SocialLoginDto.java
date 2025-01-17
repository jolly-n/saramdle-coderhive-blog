package net.blogteamthreecoderhivebe.global.auth.dto;

import lombok.Builder;
import net.blogteamthreecoderhivebe.domain.member.constant.MemberRole;
import net.blogteamthreecoderhivebe.domain.member.entity.Member;

import java.util.Map;

@Builder
public record SocialLoginDto( // String nickname,
                              String email,
                              String nameAttributeKey,
                              Map<String, Object> attributes
) {
    public static SocialLoginDto of(String registrationId, String nameAttributeKey, Map<String, Object> attributes) {
        if (registrationId.toUpperCase().equals("KAKAO")) {
            return ofKakao(KakaoOAuth2Response.from(attributes), nameAttributeKey, attributes);
        }
        if (registrationId.toUpperCase().equals("NAVER")) {
            return ofNaver(NaverOAuth2Response.from(attributes), nameAttributeKey, attributes);
        }
        return ofGoogle(GoogleOAuth2Response.from(attributes), nameAttributeKey, attributes);
    }

    private static SocialLoginDto ofKakao(KakaoOAuth2Response kakaoResponse,
                                          String nameAttributeKey,
                                          Map<String, Object> attributes) {
        return SocialLoginDto.builder()
                //.nickname(kakaoResponse.nickname())
                .email(kakaoResponse.email())
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    private static SocialLoginDto ofNaver(NaverOAuth2Response naverResponse,
                                          String nameAttributeKey,
                                          Map<String, Object> attributes) {
        return SocialLoginDto.builder()
                //.nickname(naverResponse.nickname())
                .email(naverResponse.email())
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    private static SocialLoginDto ofGoogle(GoogleOAuth2Response googleResponse,
                                           String nameAttributeKey,
                                           Map<String, Object> attributes) {
        return SocialLoginDto.builder()
                //.nickname(naverResponse.nickname())
                .email(googleResponse.email())
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .memberRole(MemberRole.GUEST)
                .build();
    }
}
