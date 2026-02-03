package fr.charles.algovisualizer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static class CacheControlInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            if (!response.containsHeader("Cache-Control")) {
                response.setHeader("Cache-Control", "max-age=" + Duration.ofHours(1).toSeconds());
            }
            // Additional security headers (complementary to Spring Security)
            response.setHeader("X-Content-Type-Options", "nosniff");
            return true;
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CacheControlInterceptor());
    }
}
