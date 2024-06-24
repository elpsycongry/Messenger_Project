package com.example.messenger.security;

import com.example.messenger.user.UserDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Extends OncePerRequestFilter để lọc mỗi khi có 1 request gửi tới
// Trong OncePerRequestFilter có Filter bean nên nó có thể lọc được
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authorizationHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        // Phân quyền cho người dùng chưa có token hoặc là chưa được xác thực
        // Để check chưa được xác thực thì hàm get
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetail userDetail = (UserDetail) this.userDetailsService.loadUserByUsername(userEmail);
            // Nếu user trong token được tạo trùng với userTrong token nhận về
            boolean isValidToken = jwtService.checkToken(jwt, userDetail);
            if (isValidToken) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetail,
                        null,
                        userDetail.getAuthorities()
                );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
