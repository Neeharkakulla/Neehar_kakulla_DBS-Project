package com.dbs.registration.config;

import com.dbs.registration.entity.request.UserRequest;
import com.dbs.registration.service.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.dbs.registration.service")
public class SecurityConfig {

	@Autowired
	UserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(getCorsConfig()))
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers("/api/v1/dbs/registration").permitAll().anyRequest().authenticated())
				.formLogin(formLogin -> formLogin.loginProcessingUrl("/login").usernameParameter("username")
						.passwordParameter("password").successHandler(this::loginSuccessHandler)
						.failureHandler(this::loginFailureHandler).permitAll());

		return http.build();
	}

	private CorsConfigurationSource getCorsConfig() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(encodePwd());
		return provider;
	}

	private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		PrintWriter writer = response.getWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		if (authentication instanceof UsernamePasswordAuthenticationToken token && token != null
				&& token.getPrincipal() instanceof UserDetailsImpl userDetails) {

			objectMapper.writeValue(writer, UserRequest.builder().username(userDetails.getUsername()).build());
		}

		writer.flush();
	}

	private void loginFailureHandler(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		PrintWriter writer = response.getWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(writer, "Login failed");
		writer.flush();
	}
}
