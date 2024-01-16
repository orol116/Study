package com.rest.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>  {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf(AbstractHttpConfigurer::disable)
			// disable session
	        .sessionManagement((sessionManagement) ->
	                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        		)
	        .authorizeHttpRequests((authorizeRequests)->
		            authorizeRequests
	                    //users 포함한 end point 보안 적용 X
	                    .requestMatchers("/v2/api-docs").permitAll() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
	                    .requestMatchers("/swagger-resources/**").permitAll()
	                    .requestMatchers("/swagger-ui.html").permitAll()
	                    .requestMatchers("/webjars/**").permitAll()
	                    .requestMatchers("/swagger/**").permitAll()
	                    .anyRequest().authenticated() // 그 외 인증 없이 접근X
	        		);
		
		return http.build();
	}
	
}