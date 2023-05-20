package com.Bikkadit.blog.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticatinFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	/**
	 * @author PAllavi Yeola
	 * @apiNote Call this Method When Api Request Hits
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 1.Get token
		String requestToken = request.getHeader(SecurityConstant.HEADER_STRING);

		// We wil get Token in Bearer terfduanmdkjfj123jdbkmdn this format

		System.out.println(requestToken);

		String userName = null;

		String actualToken = null;

		if (requestToken != null && requestToken.startsWith(SecurityConstant.TOKEN_PREFIX))
		{
			actualToken = requestToken.substring(SecurityConstant.TOKEN_INDEX);
			try {
				userName = this.jwtTokenHelper.getUserNameFromToken(actualToken);
			} catch (IllegalArgumentException e) {
				System.out.println("unable To get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWt Token Has Expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT");
			}

		} else {
			System.out.println("Jwt Token Doesnot Begins With Bearer");
		}

		// Once We Get The Token Now We Validate Our Token

		// check username is not null and Securitycontext has no hold of another
		// Authentication

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

			Boolean validateToken = this.jwtTokenHelper.validateToken(actualToken, userDetails);

			if (validateToken) {
				// If True Then we Have to Don Athenticacation With Security Context holder
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("Invalid Jwt Token");
			}
		} else {
			System.out.println("User name is null And Context has Another Authentication Hold");
		}

		filterChain.doFilter(request, response);
	}

}
