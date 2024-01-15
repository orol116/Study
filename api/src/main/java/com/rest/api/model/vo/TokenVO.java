package com.rest.api.model.vo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class TokenVO {
	
	private String grantType;
	private String accessToken;
	private String refreshToken;

}