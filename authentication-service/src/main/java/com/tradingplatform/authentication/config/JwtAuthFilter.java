package com.tradingplatform.authentication.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tradingplatform.authentication.service.CustomUserDetailsService;
import com.tradingplatform.authentication.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("AUTHORIZATION");
		final String userEmail;
		final String jwtToken;

		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwtToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(jwtToken);
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
			if (jwtService.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}