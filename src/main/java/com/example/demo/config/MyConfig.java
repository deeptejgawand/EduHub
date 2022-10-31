package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	public MyAuthenticationHandler myAuth;
	@Autowired
	public MyAuthFailHandler myFailAuth;
	
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passEncode() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider daoAuthProvide() {
		DaoAuthenticationProvider dap=new DaoAuthenticationProvider();
		dap.setUserDetailsService(this.getUserDetailsService());
		dap.setPasswordEncoder(passEncode());
		
		return dap;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(daoAuthProvide());
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/student/**")
		.hasRole("STUDENT").antMatchers("/teacher/**").hasRole("TEACHER").antMatchers("/**").permitAll().and().formLogin()
		.usernameParameter("userid").passwordParameter("password").loginPage("/login").successHandler(myAuth).failureHandler(myFailAuth).and().exceptionHandling().and().csrf().disable();
		
		
		
		
		
	}
	
	

}
