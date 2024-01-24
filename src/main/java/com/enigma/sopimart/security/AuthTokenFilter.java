package com.enigma.sopimart.security;


import com.enigma.sopimart.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    // Onceperrequest hanya menjalankan sekali untuk req tokennya

    private final JwtUtil jwtUtil;
    private final UserService userService;

    // dimana nanti do filter internal ini butuh req, res, butuh filternya, ini untuk validasi lewat header dan barier
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            // validasi token jwt
            String headerAuth = request.getHeader("Authorization");
            String token = null;
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                token = headerAuth.substring(7);
            }
            if (token != null && jwtUtil.verifyJwtToken(token)){
                // set auth ke security spring
                Map<String, String> userInfo = jwtUtil.getUserInfoByToken(token);
                UserDetails user = userService.loadUserByUserId(userInfo.get("userId"));
                // validasi token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                // menambahkan informasii berupa alamat IP ke host dalam bentuk security
                authenticationToken.setDetails(new WebAuthenticationDetailsSource());

                // menyimpan auth ke spring contex
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
}
