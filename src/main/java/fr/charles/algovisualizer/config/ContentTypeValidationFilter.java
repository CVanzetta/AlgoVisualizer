package fr.charles.algovisualizer.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ContentTypeValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String method = request.getMethod();
        String contentType = request.getContentType();
        String path = request.getRequestURI();
        
        // Validate Content-Type for POST/PUT requests on API endpoints
        if (("POST".equals(method) || "PUT".equals(method)) && path.startsWith("/api/")) {
            if (contentType == null || 
                !(contentType.contains(MediaType.APPLICATION_JSON_VALUE) || 
                  contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE))) {
                response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Content-Type must be application/json\"}");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
