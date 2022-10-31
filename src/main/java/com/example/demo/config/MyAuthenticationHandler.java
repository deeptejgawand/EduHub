package com.example.demo.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
@Component
public class MyAuthenticationHandler implements AuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		boolean hasAdminRole=false;
		boolean hasTeacherRole=false;
		boolean hasStudentRole=false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_TEACHER")) {
				hasTeacherRole = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				hasAdminRole = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_STUDENT")) {
				hasStudentRole = true;
				break;
			}
		}
		if (hasAdminRole) {
			redirectStrategy.sendRedirect(request, response, "/admin/home");
		} else if (hasStudentRole) {
			redirectStrategy.sendRedirect(request, response, "/student/home");
		} else if (hasTeacherRole) {
			redirectStrategy.sendRedirect(request, response, "/teacher/showSession");
		} 
		
		else {
			throw new IllegalStateException();
		}
		
	}

}
