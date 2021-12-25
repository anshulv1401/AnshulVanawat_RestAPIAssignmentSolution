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

	@Override
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

		http.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.POST, "/employees").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.PUT, "/employees/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.GET, "/employees/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.DELETE, "/employees/**").hasAnyAuthority("ADMIN", "USER")

				.antMatchers(HttpMethod.POST, "/roles").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.PUT, "/roles/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/roles/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.DELETE, "/roles/**").hasAnyAuthority("ADMIN", "USER")

				.antMatchers(HttpMethod.POST, "/users").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.PUT, "/users/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("ADMIN", "USER")

				.anyRequest().authenticated().and().formLogin().loginProcessingUrl("/login").permitAll().and().logout()
				.logoutSuccessUrl("/login").permitAll().and().exceptionHandling().accessDeniedPage("/accessDenied")
				.and().cors().and().csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}

}