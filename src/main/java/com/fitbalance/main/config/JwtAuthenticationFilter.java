/*
 * package com.fitbalance.main.config;
 * 
 * import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException;
 * import jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse; import
 * org.springframework.security.core.context.SecurityContextHolder; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.web.authentication.
 * WebAuthenticationDetailsSource; import
 * org.springframework.security.web.authentication.preauth.
 * PreAuthenticatedAuthenticationToken; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.filter.OncePerRequestFilter;
 * 
 * import com.fitbalance.main.services.UserDetailsServiceImpl;
 * 
 * import java.io.IOException;
 * 
 * @Component public class JwtAuthenticationFilter extends OncePerRequestFilter
 * {
 * 
 * private final JwtTokenProvider jwtTokenProvider; private final
 * UserDetailsServiceImpl userDetailsServiceImpl;
 * 
 * public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
 * UserDetailsServiceImpl customUserDetailsService) { this.jwtTokenProvider =
 * jwtTokenProvider; this.userDetailsServiceImpl = customUserDetailsService; }
 * 
 * @Override protected void doFilterInternal(HttpServletRequest request,
 * HttpServletResponse response, FilterChain filterChain) throws
 * ServletException, IOException {
 * 
 * String jwt = jwtTokenProvider.getJwtFromRequest(request); if (jwt != null &&
 * jwtTokenProvider.validateToken(jwt)) { String username =
 * jwtTokenProvider.getUsernameFromJWT(jwt);
 * 
 * UserDetails userDetails =
 * userDetailsServiceImpl.loadUserByUsername(username);
 * PreAuthenticatedAuthenticationToken authentication = new
 * PreAuthenticatedAuthenticationToken(userDetails, null,
 * userDetails.getAuthorities()); authentication.setDetails(new
 * WebAuthenticationDetailsSource().buildDetails(request));
 * 
 * SecurityContextHolder.getContext().setAuthentication(authentication); }
 * 
 * filterChain.doFilter(request, response); } }
 */
