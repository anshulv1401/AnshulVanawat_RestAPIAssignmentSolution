package com.greatlearning.ems.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.greatlearning.ems.service.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.httpBasic()
		.and().authorizeRequests()
		//.antMatchers(HttpMethod.GET, "/api/employees/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/api/employees").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.PUT, "/api/employees/**").hasAuthority("ADMIN")
		//.antMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().loginProcessingUrl("/login").permitAll()
		.and().logout().logoutSuccessUrl("/login").permitAll()
		.and().exceptionHandling().accessDeniedPage("/accessDenied")
		.and().cors().and().csrf().disable();

//		http
//        //HTTP Basic authentication
//        .httpBasic()
//        .and()
//        .authorizeRequests()
//        .antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
//        .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
//        .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
//        .antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
//        .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
//        .and()
//        .csrf().disable()
//        .formLogin().disable();
//		
		
//		http.authorizeRequests()
//		.antMatchers("/", "/roles", "/users", "/api/employees").hasAnyAuthority("USER", "ADMIN")
//		.antMatchers("/api/employees/add").hasAuthority("ADMIN")
//		.anyRequest().authenticated()
//		.and().formLogin().loginProcessingUrl("/login").permitAll()
//		.and().logout().logoutSuccessUrl("/login").permitAll()
//		.and().exceptionHandling().accessDeniedPage("/accessDenied")
//		.and().cors().and().csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}
	
}