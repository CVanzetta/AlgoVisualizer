package fr.charles.algovisualizer.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.*;

@Component
public class RequestTimeoutFilter extends OncePerRequestFilter {

    private static final long TIMEOUT_MS = 30000; // 30 seconds timeout
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // Apply timeout only on API endpoints
        if (path.startsWith("/api/")) {
            Future<?> future = executor.submit(() -> {
                try {
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            try {
                future.get(TIMEOUT_MS, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                future.cancel(true);
                if (!response.isCommitted()) {
                    response.setStatus(HttpServletResponse.SC_REQUEST_TIMEOUT);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Request timeout. Operation took too long.\"}");
                }
            } catch (Exception e) {
                if (!response.isCommitted()) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Internal server error.\"}");
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        executor.shutdownNow();
        super.destroy();
    }
}
