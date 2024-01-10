package com.rest.api.controller.v1;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.api.entity.Users;
import com.rest.api.repository.UserJpaRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"1. User"})
@RequiredArgsConstructor // 클래스 상단에 선언하면 class 내부에 final로 선언된 객체에 대해서 의존성 주입을 수행한다.
						 // 새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다. (@Autowired를 사용하지 않고 의존성 주입)
@RestController
@RequestMapping("/v1")
public class UserController {
	
	private final UserJpaRepository userJpaRepository;
	
	@ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다.")
	@GetMapping("/user")
	public List<Users> findAllUser() {
		return userJpaRepository.findAll(); // JPA 사용 시 기본 CRUD에 대해서는 별도 설정없이 쿼리를 질의할 수 있도록 메서드 지원
											// findAll()은 select rest, name, uid from user; 쿼리를 내부적으로 실행시켜줌
	}
	
	@ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
	@PostMapping("/user")
	public Users save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid
					, @ApiParam(value = "회원이름",   required = true) @RequestParam String name) {
		Users user = Users.builder()
				.uid("orol116@naver.com")
				.name("윤주빈")
				.build();
		return userJpaRepository.save(user); // USER 테이블에 데이터를 1건 입력함.
											 // .save(user); 역시 JPA에서 기본 제공하는 메서드
											 // user 객체를 전달하면 다음과 같이 내부적으로 insert문을 실행시켜줌
											 // insert into user (rest, name, uid) values (null, ?, ?);
	}

}
