package com.rest.api.controller.v1;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.api.config.security.JwtTokenProvider;
import com.rest.api.entity.Users;
import com.rest.api.exception.CEmailSigninFailedException;
import com.rest.api.model.CommonResult;
import com.rest.api.model.SingleResult;
import com.rest.api.repository.UserJpaRepository;
import com.rest.api.service.ResponseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
@Tag(name = "sign", description = "로그인 영역 API")
public class SignController {
	
	private final UserJpaRepository userJpaRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;
	private final PasswordEncoder passwordEncoder;
	
	@Operation(summary = "회원 로그인", description = "이메일 회원 로그인을 한다.")
	@PostMapping("/signin")
	public SingleResult<String> signin(@RequestParam String id
									 , @RequestParam String password) {
		
		Users users = userJpaRepository.findByUid(id).orElseThrow(CEmailSigninFailedException::new);
		if (!passwordEncoder.matches(password, users.getPassword())) {
			throw new CEmailSigninFailedException();
		}
		
		return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(users.getMsrl()), users.getRoles()));
	}
	
	@Operation(summary = "회원 가입", description = "회원 가입을 한다.")
	@PostMapping("/signup")
	public CommonResult signup(@RequestParam String id
							 , @RequestParam String password
							 , @RequestParam String name) {
		
		userJpaRepository.save(Users.builder()
									.uid(id)
									.password(passwordEncoder.encode(password))
									.name(name)
									.roles(Collections.singletonList("ROLE_USER"))
									.build());
		
		return responseService.getSuccessResult();
	}

}
