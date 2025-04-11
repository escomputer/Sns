package com.example.pstagram.config;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * BCrypt 알고리즘을 사용하여 비밀번호를 암호화하고 검증하는 유틸 클래스
 *
 * 회원가입 시 비밀번호 암호화, 로그인 시 입력값과 DB값 비교에 사용됩니다
 */
@Component
public class PasswordEncoder {

	/**
	 * 입력된 비밀번호를 BCrypt 해시로 암호화
	 *
	 * @param rawPassword 사용자가 입력한 원문 비밀번호
	 * @return 암호화된 비밀번호 문자열
	 */

	public String encode(String rawPassword) {
		return BCrypt.withDefaults()
			.hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
	}

	/**
	 * 입력한 비밀번호와 암호화된 비밀번호가 일치하는지 검증합니다.
	 *
	 * @param rawPassword     사용자가 입력한 비밀번호
	 * @param encodedPassword DB에 저장된 암호화된 비밀번호
	 * @return 일치 여부 (true/false)
	 */

	public boolean matches(String rawPassword, String encodedPassword) {
		return BCrypt.verifyer()
			.verify(rawPassword.toCharArray(), encodedPassword)
			.verified;
	}
}
