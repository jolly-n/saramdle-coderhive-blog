package net.blogteamthreecoderhivebe.global.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.blogteamthreecoderhivebe.global.auth.dto.MemberPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();
        String email = principal.getEmail();

        log.info("oauth login success : {} {}", email, authentication.getAuthorities());

        String redirectUrl;
        if (principal.isGuest()) {
            // GUEST일 경우 회원 가입 페이지로 이동
            redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/register")
                    .queryParam("email", email)
                    .build()
                    .toUriString();
        } else {
            // USER일 경우 홈로 이동
            redirectUrl = "http://localhost:3000";
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
