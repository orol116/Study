package com.rest.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder // builder 사용할 수 있게 해줌
@Entity  // Jpa entity임을 알림
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // PK생성전략을 위해 DB에 위임
	@Schema(description = "PK")
	private long msrl;
	
	@Column(nullable = false, unique = true, length = 30) // uid 컬럼을 명시한다. 필수이고 유니크한 필드이며 길이는 30
	@Schema(description = "회원 아이디")
	private String uid;
	
	@Column(nullable = false, length = 100)
	@Schema(description = "회원 이름")
	private String name;

}