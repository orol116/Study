package com.rest.api.config.security;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.rest.api.entity.Users;
import com.rest.api.model.vo.TokenVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Slf4j
@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈
	
	private final String AT_JWT_SECRET = "accessToken";
	private final String RT_JWT_SECRET = "refreshToken";
	private final String TOKEN_PREFIX = "Bearer";
	private final int AT_EXPIRATION_MS = 1800000; // 30분
	private final int RT_EXPIRATION_MS = 86400000; // 1주
	
	/**
	 * JWT 토큰 생성
	 * @param users
	 * @return
	 */
	public TokenVO generateToken(Users users) {
		return TokenVO.builder()
				.grantType(TOKEN_PREFIX)
				.accessToken(createAccessToken(users))
				.refreshToken(createAccessToken(users))
				.build();
	}
	
	public String createAccessToken(Users users) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + AT_EXPIRATION_MS);
		
		return Jwts.builder()
				.setSubject("USER")
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.claim("uid", users.getUid())
				.claim("name", users.getName())
				.claim("msrl", users.getMsrl())
				.signWith(SignatureAlgorithm.HS512, AT_JWT_SECRET)
				.compact();
	}
	
	public String createRefreshToken(Users users) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + RT_EXPIRATION_MS);
		
		return Jwts.builder()
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.claim("uid", users.getUid())
				.claim("name", users.getName())
				.claim("msrl", users.getMsrl())
				.signWith(SignatureAlgorithm.HS512, RT_JWT_SECRET)
				.compact();
	}
	
	public boolean validateToken(String token) {
		return validateToken(token, null);
	}
	 
	/**
	 * JWT Access 토큰 유효성 검사
	 * @param token
	 * @param keyType
	 * @return
	 */
	public boolean validateToken(String token, String keyType) {
		try {
			Jws<Claims> claims = null;
			if ("refresh".equals(keyType)) {
				claims = Jwts.parser().setSigningKey(RT_JWT_SECRET).parseClaimsJws(token);
			} else {
				claims = Jwts.parser().setSigningKey(AT_JWT_SECRET).parseClaimsJws(token);
			}
			return !claims.getBody().getExpiration().before(new Date());
		} catch (SignatureException e) {
            log.info("Invalid JWT Signature");
        } catch (MalformedJwtException e) {
        	log.info("Expired JWT Token");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.");
        } catch (Exception e) {
        	log.info("JWT Error");
        }
		
		return false;
	}
	
	public TokenVO recreationAccessToken(String refreshToken) {
		try {
			// refresh claim
			Jws<Claims> claims = Jwts.parser().setSigningKey(RT_JWT_SECRET).parseClaimsJws(refreshToken);
			
			// 만료기간 체크
			if (validateToken(refreshToken, "refresh")) {
				Users users = new Users();
				users.setUid(claims.getBody().get("uid").toString());
				users.setName(claims.getBody().get("name").toString());
				
				return TokenVO.builder()
						.grantType("Bearer")
						.accessToken(createAccessToken(users))
						.refreshToken(refreshToken)
						.build();
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}
	
}