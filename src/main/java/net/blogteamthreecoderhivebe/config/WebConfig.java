package net.blogteamthreecoderhivebe.config;

import net.blogteamthreecoderhivebe.converter.CareerRequestConverter;
import net.blogteamthreecoderhivebe.converter.LevelRequestConverter;
import net.blogteamthreecoderhivebe.converter.PlatformRequestConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://coderhive.vercel.app");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CareerRequestConverter());
        registry.addConverter(new LevelRequestConverter());
        registry.addConverter(new PlatformRequestConverter());
    }


}