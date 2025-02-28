package users.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public AuthTokenFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }


    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        request.setAttribute("claims", ex.getClaims());

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {
            final String authorizationHeader = request.getHeader("Authorization");

            String username = null;
            String jwtToken = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwtToken = authorizationHeader.substring(7);
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (ExpiredJwtException ex) {
            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("auth")) {
                allowForRefreshToken(ex, request);
            } else
                request.setAttribute("exception", ex);
        }

        filterChain.doFilter(request, response);
    }
}