package com.rest.api.controller.v1;

//import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.api.entity.Users;
import com.rest.api.exception.CUserNotFoundException;
import com.rest.api.model.CommonResult;
import com.rest.api.model.ListResult;
import com.rest.api.model.SingleResult;
import com.rest.api.repository.UserJpaRepository;
import com.rest.api.service.ResponseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 클래스 상단에 선언하면 class 내부에 final로 선언된 객체에 대해서 의존성 주입을 수행한다.
						 // 새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다. (@Autowired를 사용하지 않고 의존성 주입)
@RestController
@RequestMapping("/v1")
@Tag(name = "users", description = "회원 영역 API")
public class UserController {
	
	private final UserJpaRepository userJpaRepository;
	private final ResponseService responseService;
	
	@Operation(summary = "회원 리스트 조회", description = "회원 리스트를 조회한다.")
	@GetMapping("/users")
	public ListResult<Users> findAllUser() {
		
		// 결과 데이터가 여러 건인 경우 getListResult를 이용해서 결과 출력
		return responseService.getListResult(userJpaRepository.findAll());
		
//		return userJpaRepository.findAll(); // JPA 사용 시 기본 CRUD에 대해서는 별도 설정없이 쿼리를 질의할 수 있도록 메서드 지원
											// findAll()은 select rest, name, uid from user; 쿼리를 내부적으로 실행시켜줌
	}
	
	@Operation(summary = "회원 단건 조회", description = "userId로 회원을 조회한다.")
	@GetMapping("/users/{msrl}")
	public SingleResult<Users> findUserById(@PathVariable long msrl
										  , @RequestParam String lang) {
		
		// 결과 데이터가 단일 건인 경우 getBasicResult를 이용해서 결과를 출력한다.
		return responseService.getSingleResult(userJpaRepository.findById(msrl).orElseThrow(CUserNotFoundException::new));
	}
	
	@Operation(summary = "회원 등록", description = "회원을 저장한다.")
	@PostMapping("/users")
	public SingleResult<Users> save(@RequestParam("uid")  String uid   // swagger 테스트 시 파라미터 정확한 입력을 위한 value 설정
								  , @RequestParam("name") String name) {
		Users user = Users.builder()
				.uid(uid)
				.name(name)
				.build();
		return responseService.getSingleResult(userJpaRepository.save(user));
		
//		return userJpaRepository.save(user); // USER 테이블에 데이터를 1건 입력함.
											 // .save(user); 역시 JPA에서 기본 제공하는 메서드
											 // user 객체를 전달하면 다음과 같이 내부적으로 insert문을 실행시켜줌
											 // insert into user (rest, name, uid) values (null, ?, ?);
	}
	
	@Operation(summary = "회원 수정", description = "회원 정보를 수정한다.")
	@PutMapping("/users")
	public SingleResult<Users> modify(@RequestParam("msrl") long msrl
									, @RequestParam("uid")  String uid
									, @RequestParam("name") String name) {
		
		Users users = Users.builder()
				.msrl(msrl)
				.uid(uid)
				.name(name)
				.build();
		return responseService.getSingleResult(userJpaRepository.save(users));
	}
	
	@Operation(summary = "회원 삭제", description = "userId로 회원 정보를 삭제한다.")
	@DeleteMapping("/users/{msrl}")
	public CommonResult delete(@PathVariable long msrl) {
		
		userJpaRepository.deleteById(msrl);
		
		// 성공 결과 정보만 필요한 경우 getSuccessResult()를 이용하여 결과 출력
		return responseService.getSuccessResult();
	}

}
