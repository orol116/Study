package com.rest.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final JwtTokenProvider jwtTokenProvider;

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		
//		http.csrf(AbstractHttpConfigurer::disable)
//			// disable session
//	        .sessionManagement((sessionManagement) ->
//	                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	        		)
//	        .authorizeHttpRequests((authorizeRequests)->
//		            authorizeRequests
//	                    .requestMatchers("/v2/api-docs").permitAll() // HttpServletRequest를 사용하는 요청들에 대한 접근제한 설정
//	                    .requestMatchers("/swagger-resources/**").permitAll()
//	                    .requestMatchers("/swagger-ui.html").permitAll()
//	                    .requestMatchers("/webjars/**").permitAll()
//	                    .requestMatchers("/swagger/**").permitAll()
//	                    .anyRequest().authenticated() // 그 외 인증 없이 접근X
//	        		);
//		
//		return http.build();
//	}
	
    @Bean
    public SecurityFilterChain filterChain(final @NotNull HttpSecurity http) throws Exception {
        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                );
        return http.build();
    }
	
}