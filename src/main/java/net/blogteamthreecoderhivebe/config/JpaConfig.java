package net.blogteamthreecoderhivebe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
//    @Bean
//    public AuditorAware<String> auditorAware() {
//        return () -> Optional.ofNullable("coderhive");
//    }
}
